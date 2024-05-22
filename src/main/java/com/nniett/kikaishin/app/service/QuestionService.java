package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.entity.virtual.ReviewableQuestionVirtualEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.persistence.repository.ClueRepository;
import com.nniett.kikaishin.app.persistence.repository.QuestionRepository;
import com.nniett.kikaishin.app.persistence.repository.virtual.QuestionInfoVirtualRepository;
import com.nniett.kikaishin.app.persistence.repository.virtual.ReviewableQuestionVirtualRepository;
import com.nniett.kikaishin.app.service.construction.ActivateableService;
import com.nniett.kikaishin.app.service.crud.question.QuestionCreateService;
import com.nniett.kikaishin.app.service.crud.question.QuestionDeleteService;
import com.nniett.kikaishin.app.service.crud.question.QuestionReadService;
import com.nniett.kikaishin.app.service.crud.question.QuestionUpdateService;
import com.nniett.kikaishin.app.service.dto.ReviewableQuestionDto;
import com.nniett.kikaishin.app.service.forget.risk.RiskFactorsManager;
import com.nniett.kikaishin.app.service.mapper.QuestionInfoMapper;
import com.nniett.kikaishin.app.service.mapper.ReviewableQuestionMapper;
import com.nniett.kikaishin.app.service.dto.LabeledRecommendedQuestionListDto;
import com.nniett.kikaishin.app.service.dto.QuestionDto;
import com.nniett.kikaishin.app.service.dto.QuestionInfoDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionCreationDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionUpdateDto;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class QuestionService
        extends ActivateableService
        <
                QuestionEntity,
                Integer,
                QuestionDto,
                QuestionCreationDto,
                QuestionUpdateDto
                >
        implements UsesHttpServletRequest, CanRetrieveUsernameFromJWT
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final ReviewableQuestionMapper reviewableQuestionMapper;
    private final QuestionInfoVirtualRepository questionInfoRepository;
    private final QuestionInfoMapper questionInfoMapper;
    private final ReviewableQuestionVirtualRepository reviewableQuestionVirtualRepository;
    private final AnswerRepository answerRepository;
    private final ClueRepository clueRepository;
    private final JwtUtils jwtUtils;

    @Autowired
    public QuestionService(
            QuestionRepository repository,
            QuestionCreateService createService,
            QuestionReadService readService,
            QuestionUpdateService updateService,
            QuestionDeleteService deleteService,
            ReviewableQuestionMapper reviewableQuestionMapper,
            QuestionInfoVirtualRepository questionInfoRepository,
            QuestionInfoMapper questionInfoMapper,
            ReviewableQuestionVirtualRepository reviewableQuestionVirtualRepository,
            AnswerRepository answerRepository,
            ClueRepository clueRepository,
            JwtUtils jwtUtils
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.reviewableQuestionMapper = reviewableQuestionMapper;
        this.questionInfoRepository = questionInfoRepository;
        this.questionInfoMapper = questionInfoMapper;
        this.reviewableQuestionVirtualRepository = reviewableQuestionVirtualRepository;
        this.answerRepository = answerRepository;
        this.clueRepository = clueRepository;
        this.jwtUtils = jwtUtils;
        logger.info("QuestionService initialized.");
    }


    @Override
    public void populateAsDefaultForCreation(QuestionEntity entity) {
        getCreateService().populateAsDefaultForCreation(entity);
    }

    @Override
    public void populateEntityForUpdate(QuestionEntity entity, QuestionDto pojo) {
        getUpdateService().populateEntityForUpdate(entity, pojo);
    }

    @Override
    public QuestionEntity findEntityByDto(QuestionUpdateDto updateDto) {
        return getRepository().findById(updateDto.getQuestionId()).orElseThrow();
    }

    @Override
    public ListCrudRepository<QuestionEntity, Integer> getActivateableRepository() {
        return getRepository();
    }

    public List<ReviewableQuestionDto> getReviewSet(
            String reviewLevel,
            Integer containerId,
            Integer totalQuestions,
            Boolean forced
    ) {
        List<ReviewableQuestionVirtualEntity> prospectQuestions;
        List<ReviewableQuestionVirtualEntity> finalSetOfQuestions = new ArrayList<>();
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.debug("Retrieving review set.");
        switch(reviewLevel.toUpperCase()) {
            case "ALL": {
                prospectQuestions = reviewableQuestionVirtualRepository.getAllReviewableQuestions(username);
            } break;
            case "SHELF": {
                prospectQuestions = reviewableQuestionVirtualRepository.getShelfReviewableQuestions(username, containerId);
            } break;
            case "BOOK": {
                prospectQuestions = reviewableQuestionVirtualRepository.getBookReviewableQuestions(username, containerId);
            } break;
            case "TOPIC": {
                prospectQuestions = reviewableQuestionVirtualRepository.getTopicReviewableQuestions(username, containerId);
            } break;
            default: {
                prospectQuestions = null;
            } break;
        }

        if(prospectQuestions != null) {
            logger.trace("Total prospect questions retrieved: {}.", prospectQuestions.size());
            logger.debug("Sorting, calculating reviewability and limiting review set.");
            logger.trace("Is forced set: {}.", forced);
            //Ordered by the respective review sequence calculated by review model through "Comparable" mechanism.
            Collections.sort(prospectQuestions);
            for(
                    int i = 0;
                    //while i is smaller that expected review size or prospect gathered set (the smallest of the two).
                    i < (prospectQuestions.size() > totalQuestions ? totalQuestions : prospectQuestions.size());
                    i++)
            {
                ReviewableQuestionVirtualEntity prospectQuestion = prospectQuestions.get(i);
                if(forced || prospectQuestion.isToReview()) {
                    finalSetOfQuestions.add(prospectQuestion);
                    logger.trace("Retrieving answers for reviewable question with id: {}.", prospectQuestion.getQuestionId());
                    prospectQuestion.setAnswers(answerRepository.findByQuestionId(prospectQuestion.getQuestionId()));
                    logger.trace("Retrieving clues for reviewable question with id: {}.", prospectQuestion.getQuestionId());
                    prospectQuestion.setClues(clueRepository.findByQuestionId(prospectQuestion.getQuestionId()));
                }
            }
        }

        List<ReviewableQuestionDto> reviewableQuestions = this.reviewableQuestionMapper.toReviewableQuestions(finalSetOfQuestions);
        if(reviewableQuestions != null) {
            logger.debug("Providing final set order index for review.");
            for (int i = 0; i < reviewableQuestions.size(); i++) {
                reviewableQuestions.get(i).setOrderIndex(i + 1);
            }
        }
        logger.debug("Reviewable question set constructed successfully.");
        return reviewableQuestions;
    }

    public QuestionInfoDto getQuestionInfo(Integer questionId) {
        logger.debug("Retrieving question info.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Question info being gathered for username {}.", username);
        return questionInfoMapper.
                toQuestionInfo(
                        questionInfoRepository.
                                getQuestionsInfoById(username, Collections.singletonList(questionId)).get(0)
                );
    }

    public List<QuestionInfoDto> getQuestionsInfo(List<Integer> questionIds) {
        logger.debug("Retrieving questions info.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Question info being gathered for username {}.", username);
        return questionInfoMapper.toQuestionsInfo(questionInfoRepository.getQuestionsInfoById(username, questionIds));
    }

    public Integer countExistingIds(List<Integer> questionIds) {
        logger.debug("Retrieving count of questions by ids.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Counting questions by ids for username {}.", username);
        return ((QuestionRepository) getRepository()).countByIdIn(username, questionIds);
    }

    public List<LabeledRecommendedQuestionListDto> getRecommendedQuestions() {
        logger.debug("Retrieving recommended questions.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Retrieving recommended questions for username {}.", username);
        return RiskFactorsManager.getRecommendedReview(((QuestionRepository) getRepository()).findByUsername(username));
    }

    public List<ReviewableQuestionDto> getCustomReviewableList(List<Integer> questionIds) {
        logger.debug("Retrieving custom reviewable questions.");
        List<ReviewableQuestionVirtualEntity> entities =
                this.reviewableQuestionVirtualRepository.getReviewableQuestionsByIdsIn(questionIds);
        logger.trace("Total questions retrieved: {}.", entities.size());
        entities.forEach(q -> {
            logger.trace("Retrieving answers for question with id: {}.", q.getQuestionId());
            q.setAnswers(answerRepository.findByQuestionId(q.getQuestionId()));
            logger.trace("Retrieving clues for question with id: {}.", q.getQuestionId());
            q.setClues(clueRepository.findByQuestionId(q.getQuestionId()));
        });
        logger.debug("Sorting custom reviewable questions list.");
        Collections.sort(entities);
        List<ReviewableQuestionDto> reviewableQuestions = this.reviewableQuestionMapper.toReviewableQuestions(entities);
        logger.debug("Setting custom reviewable questions list order index.");
        for (int i = 0; i < reviewableQuestions.size(); i++) {
            reviewableQuestions.get(i).setOrderIndex(i + 1);
        }
        logger.debug("Custom reviewable questions constructed successfully.");
        return reviewableQuestions;
    }
}
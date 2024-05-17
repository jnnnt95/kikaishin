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
import com.nniett.kikaishin.app.service.forget.risk.RiskFactorsManager;
import com.nniett.kikaishin.app.service.mapper.QuestionInfoMapper;
import com.nniett.kikaishin.app.service.mapper.ReviewableQuestionMapper;
import com.nniett.kikaishin.app.service.dto.LabeledRecommendedQuestionListDto;
import com.nniett.kikaishin.app.service.dto.QuestionDto;
import com.nniett.kikaishin.app.service.dto.QuestionInfoDto;
import com.nniett.kikaishin.app.service.dto.ReviewableQuestion;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionCreationDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionUpdateDto;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
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
        int id = updateDto.getQuestionId();
        return getRepository().findById(id).orElseThrow();
    }

    @Override
    public ListCrudRepository<QuestionEntity, Integer> getActivateableRepository() {
        return getRepository();
    }

    public List<ReviewableQuestion> getReviewSet(
            String reviewLevel,
            Integer containerId,
            Integer totalQuestions,
            Boolean forced
    ) {
        List<ReviewableQuestionVirtualEntity> prospectQuestions;
        List<ReviewableQuestionVirtualEntity> finalSetOfQuestions = new ArrayList<>();
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        switch(reviewLevel) {
            case "all": {
                prospectQuestions = reviewableQuestionVirtualRepository.getAllReviewableQuestions(username);
            } break;
            case "shelf": {
                prospectQuestions = reviewableQuestionVirtualRepository.getShelfReviewableQuestions(username, containerId);
            } break;
            case "book": {
                prospectQuestions = reviewableQuestionVirtualRepository.getBookReviewableQuestions(username, containerId);
            } break;
            case "topic": {
                prospectQuestions = reviewableQuestionVirtualRepository.getTopicReviewableQuestions(username, containerId);
            } break;
            default: {
                prospectQuestions = null;
            } break;
        }

        if(prospectQuestions != null) {
            //Ordered by the respective review sequence calculated by review model through "Comparable" mechanism.
            Collections.sort(prospectQuestions);
            for(int i = 0; i < (prospectQuestions.size() > totalQuestions ? totalQuestions : prospectQuestions.size()); i++) {
                ReviewableQuestionVirtualEntity rq = prospectQuestions.get(i);
                if(forced || rq.isToReview()) {
                    finalSetOfQuestions.add(rq);
                    rq.setAnswers(answerRepository.findByQuestionId(rq.getQuestionId()));
                    rq.setClues(clueRepository.findByQuestionId(rq.getQuestionId()));
                }
            }
        }

        List<ReviewableQuestion> reviewableQuestions = this.reviewableQuestionMapper.toReviewableQuestions(finalSetOfQuestions);
        if(reviewableQuestions != null) {
            for (int i = 0; i < reviewableQuestions.size(); i++) {
                reviewableQuestions.get(i).setOrderIndex(i + 1);
            }
        }
        return reviewableQuestions;
    }

    public QuestionInfoDto getQuestionInfo(Integer questionId) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return questionInfoMapper.
                toQuestionInfo(
                        questionInfoRepository.
                                getQuestionsInfoById(username, Collections.singletonList(questionId)).get(0)
                );
    }

    public List<QuestionInfoDto> getQuestionsInfo(List<Integer> questionIds) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return questionInfoMapper.toQuestionsInfo(questionInfoRepository.getQuestionsInfoById(username, questionIds));
    }

    public Integer countExistingIds(List<Integer> questionIds) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return ((QuestionRepository) getRepository()).countByIdIn(username, questionIds);
    }

    public List<LabeledRecommendedQuestionListDto> getRecommendedQuestions() {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return RiskFactorsManager.getRecommendedReview(((QuestionRepository) getRepository()).findByUsername(username));
    }

    public List<ReviewableQuestion> getCustomReviewableList(List<Integer> questionIds) {
        List<ReviewableQuestionVirtualEntity> entities =
                this.reviewableQuestionVirtualRepository.getReviewableQuestionsByIdsIn(questionIds);
        entities.forEach(q -> {
            q.setAnswers(answerRepository.findByQuestionId(q.getQuestionId()));
            q.setClues(clueRepository.findByQuestionId(q.getQuestionId()));
        });
        List<ReviewableQuestion> reviewableQuestions = this.reviewableQuestionMapper.toReviewableQuestions(entities);
        for (int i = 0; i < reviewableQuestions.size(); i++) {
            reviewableQuestions.get(i).setOrderIndex(i + 1);
        }
        return reviewableQuestions;
    }
}
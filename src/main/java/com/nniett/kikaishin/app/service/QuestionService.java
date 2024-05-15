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
import com.nniett.kikaishin.app.service.pojo.LabeledRecommendedQuestionList;
import com.nniett.kikaishin.app.service.pojo.Question;
import com.nniett.kikaishin.app.service.pojo.QuestionInfo;
import com.nniett.kikaishin.app.service.pojo.ReviewableQuestion;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionUpdateDto;
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
                Question,
                QuestionCreationDto,
                QuestionUpdateDto
                >
{
    private final ReviewableQuestionMapper reviewableQuestionMapper;
    private final QuestionInfoVirtualRepository questionInfoRepository;
    private final QuestionInfoMapper questionInfoMapper;
    private final ReviewableQuestionVirtualRepository reviewableQuestionVirtualRepository;
    private final AnswerRepository answerRepository;
    private final ClueRepository clueRepository;

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
            ClueRepository clueRepository
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.reviewableQuestionMapper = reviewableQuestionMapper;
        this.questionInfoRepository = questionInfoRepository;
        this.questionInfoMapper = questionInfoMapper;
        this.reviewableQuestionVirtualRepository = reviewableQuestionVirtualRepository;
        this.answerRepository = answerRepository;
        this.clueRepository = clueRepository;
    }


    @Override
    public void populateAsDefaultForCreation(QuestionEntity entity) {
        getCreateService().populateAsDefaultForCreation(entity);
    }

    @Override
    public void populateEntityForUpdate(QuestionEntity entity, Question pojo) {
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
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
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

    public QuestionInfo getQuestionInfo(Integer questionId) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return questionInfoMapper.
                toQuestionInfo(
                        questionInfoRepository.
                                getQuestionsInfoById(username, Collections.singletonList(questionId)).get(0)
                );
    }

    public List<QuestionInfo> getQuestionsInfo(List<Integer> questionIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return questionInfoMapper.toQuestionsInfo(questionInfoRepository.getQuestionsInfoById(username, questionIds));
    }

    public Integer countExistingIds(List<Integer> questionIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return ((QuestionRepository) getRepository()).countByIdIn(username, questionIds);
    }

    public List<LabeledRecommendedQuestionList> getRecommendedQuestions() {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
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
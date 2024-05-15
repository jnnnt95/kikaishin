package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionReviewGradeRepository;
import com.nniett.kikaishin.app.persistence.repository.ReviewRepository;
import com.nniett.kikaishin.app.service.construction.ImmutableService;
import com.nniett.kikaishin.app.service.crud.questionreviewgrade.QuestionReviewGradeCreateService;
import com.nniett.kikaishin.app.service.crud.questionreviewgrade.QuestionReviewGradeDeleteService;
import com.nniett.kikaishin.app.service.crud.questionreviewgrade.QuestionReviewGradeReadService;
import com.nniett.kikaishin.app.service.crud.review.ReviewCreateService;
import com.nniett.kikaishin.app.service.crud.review.ReviewDeleteService;
import com.nniett.kikaishin.app.service.crud.review.ReviewReadService;
import com.nniett.kikaishin.app.service.pojo.QuestionReviewGrade;
import com.nniett.kikaishin.app.service.pojo.Review;
import com.nniett.kikaishin.app.service.pojo.dto.questionreviewgrade.QuestionReviewGradeCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.review.ReviewCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReviewGradeService
        extends ImmutableService
        <
                QuestionReviewGradeCreationDto,
                QuestionReviewGrade,
                QuestionReviewGradeEntity,
                Integer
                >
{

    @Autowired
    public QuestionReviewGradeService(
            QuestionReviewGradeRepository repository,
            QuestionReviewGradeCreateService createService,
            QuestionReviewGradeReadService readService,
            QuestionReviewGradeDeleteService deleteService
    ) {
        super(repository, createService, readService, deleteService);
    }

    @Override
    public void populateAsDefaultForCreation(QuestionReviewGradeEntity entity) {

    }

    @Override
    public QuestionReviewGradeEntity readEntity(Integer id) {
        return getRepository().findById(id).get();
    }
}
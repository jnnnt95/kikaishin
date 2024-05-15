package com.nniett.kikaishin.app.service.crud.questionreviewgrade;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionReviewGradeRepository;
import com.nniett.kikaishin.app.persistence.repository.ReviewRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.QuestionReviewGradeMapper;
import com.nniett.kikaishin.app.service.mapper.ReviewMapper;
import com.nniett.kikaishin.app.service.pojo.QuestionReviewGrade;
import com.nniett.kikaishin.app.service.pojo.Review;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReviewGradeReadService
        extends ReadService
        <
                QuestionReviewGradeEntity,
                Integer,
                QuestionReviewGrade
                >
{

    public QuestionReviewGradeReadService(
            QuestionReviewGradeRepository repository,
            QuestionReviewGradeMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }
    
}

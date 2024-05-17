package com.nniett.kikaishin.app.service.crud.questionreviewgrade;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionReviewGradeRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.QuestionReviewGradeMapper;
import com.nniett.kikaishin.app.service.dto.QuestionReviewGradeDto;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReviewGradeReadService
        extends ReadService
        <
                QuestionReviewGradeEntity,
                Integer,
                QuestionReviewGradeDto
                >
{

    public QuestionReviewGradeReadService(
            QuestionReviewGradeRepository repository,
            QuestionReviewGradeMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }
    
}

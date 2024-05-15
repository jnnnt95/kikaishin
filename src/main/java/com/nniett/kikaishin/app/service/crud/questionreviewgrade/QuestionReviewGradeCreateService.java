package com.nniett.kikaishin.app.service.crud.questionreviewgrade;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionReviewGradeRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.QuestionReviewGradeMapper;
import com.nniett.kikaishin.app.service.mapper.dto.questionreviewgrade.QuestionReviewGradeCreationMapper;
import com.nniett.kikaishin.app.service.pojo.QuestionReviewGrade;
import com.nniett.kikaishin.app.service.pojo.dto.questionreviewgrade.QuestionReviewGradeCreationDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReviewGradeCreateService
        extends CreateService
        <
                QuestionReviewGradeEntity,
                Integer,
                QuestionReviewGrade,
                QuestionReviewGradeCreationDto
                >
{

    public QuestionReviewGradeCreateService(
            QuestionReviewGradeRepository repository,
            QuestionReviewGradeMapper entityPojoMapper,
            @Qualifier("questionReviewGradeCreationMapperImpl")
            QuestionReviewGradeCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
    }

    @Override
    public void populateAsDefaultForCreation(QuestionReviewGradeEntity entity) {

    }
}

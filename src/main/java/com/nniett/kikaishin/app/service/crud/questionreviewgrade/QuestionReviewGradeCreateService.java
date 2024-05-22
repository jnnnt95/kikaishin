package com.nniett.kikaishin.app.service.crud.questionreviewgrade;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionReviewGradeRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.QuestionReviewGradeMapper;
import com.nniett.kikaishin.app.service.mapper.dto.questionreviewgrade.QuestionReviewGradeCreationMapper;
import com.nniett.kikaishin.app.service.dto.QuestionReviewGradeDto;
import com.nniett.kikaishin.app.service.dto.write.questionreviewgrade.QuestionReviewGradeCreationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReviewGradeCreateService
        extends CreateService
        <
                QuestionReviewGradeEntity,
                Integer,
                QuestionReviewGradeDto,
                QuestionReviewGradeCreationDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionReviewGradeCreateService.class);

    public QuestionReviewGradeCreateService(
            QuestionReviewGradeRepository repository,
            QuestionReviewGradeMapper entityPojoMapper,
            @Qualifier("questionReviewGradeCreationMapperImpl")
            QuestionReviewGradeCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
        logger.info("QuestionReviewGradeCreateService initialized.");
    }

    @Override
    public void populateAsDefaultForCreation(QuestionReviewGradeEntity entity) {}
}

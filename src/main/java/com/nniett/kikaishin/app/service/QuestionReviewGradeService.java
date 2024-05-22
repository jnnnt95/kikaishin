package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionReviewGradeRepository;
import com.nniett.kikaishin.app.service.construction.ImmutableService;
import com.nniett.kikaishin.app.service.crud.questionreviewgrade.QuestionReviewGradeCreateService;
import com.nniett.kikaishin.app.service.crud.questionreviewgrade.QuestionReviewGradeDeleteService;
import com.nniett.kikaishin.app.service.crud.questionreviewgrade.QuestionReviewGradeReadService;
import com.nniett.kikaishin.app.service.dto.QuestionReviewGradeDto;
import com.nniett.kikaishin.app.service.dto.write.questionreviewgrade.QuestionReviewGradeCreationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReviewGradeService
        extends ImmutableService
        <
                QuestionReviewGradeCreationDto,
                QuestionReviewGradeDto,
                QuestionReviewGradeEntity,
                Integer
                >
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionReviewGradeService.class);

    @Autowired
    public QuestionReviewGradeService(
            QuestionReviewGradeRepository repository,
            QuestionReviewGradeCreateService createService,
            QuestionReviewGradeReadService readService,
            QuestionReviewGradeDeleteService deleteService
    ) {
        super(repository, createService, readService, deleteService);
        logger.info("QuestionReviewGradeService initialized.");
    }

    @Override
    public void populateAsDefaultForCreation(QuestionReviewGradeEntity entity) {}

    @Override
    public QuestionReviewGradeEntity readEntity(Integer id) {
        return getRepository().findById(id).orElseThrow();
    }
}
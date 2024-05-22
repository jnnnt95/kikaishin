package com.nniett.kikaishin.app.service.crud.questionreviewgrade;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionReviewGradeRepository;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReviewGradeDeleteService
        extends DeleteService<QuestionReviewGradeEntity, Integer>
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionReviewGradeDeleteService.class);

    public QuestionReviewGradeDeleteService(QuestionReviewGradeRepository repository) {
        super(repository);
        logger.info("QuestionReviewGradeDeleteService initialized.");
    }

}

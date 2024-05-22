package com.nniett.kikaishin.app.service.crud.questionreviewgrade;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionReviewGradeRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.QuestionReviewGradeMapper;
import com.nniett.kikaishin.app.service.dto.QuestionReviewGradeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(QuestionReviewGradeReadService.class);

    public QuestionReviewGradeReadService(
            QuestionReviewGradeRepository repository,
            QuestionReviewGradeMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
        logger.info("QuestionReviewGradeReadService initialized.");
    }
    
}

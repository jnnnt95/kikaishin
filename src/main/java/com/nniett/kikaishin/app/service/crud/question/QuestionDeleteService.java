package com.nniett.kikaishin.app.service.crud.question;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionRepository;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDeleteService
        extends DeleteService<QuestionEntity, Integer>
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionDeleteService.class);

    public QuestionDeleteService(QuestionRepository repository) {
        super(repository);
        logger.info("QuestionDeleteService initialized.");
    }

}

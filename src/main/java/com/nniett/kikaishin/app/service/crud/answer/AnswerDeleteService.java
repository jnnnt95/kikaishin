package com.nniett.kikaishin.app.service.crud.answer;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerDeleteService
        extends DeleteService<AnswerEntity, Integer>
{
    private static final Logger logger = LoggerFactory.getLogger(AnswerDeleteService.class);

    public AnswerDeleteService(AnswerRepository repository) {
        super(repository);
        logger.info("AnswerDeleteService initialized.");
    }

}

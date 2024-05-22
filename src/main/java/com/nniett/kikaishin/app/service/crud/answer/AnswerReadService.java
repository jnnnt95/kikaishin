package com.nniett.kikaishin.app.service.crud.answer;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.AnswerMapper;
import com.nniett.kikaishin.app.service.dto.AnswerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerReadService
        extends ReadService
        <
                AnswerEntity,
                Integer,
                AnswerDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(AnswerReadService.class);

    public AnswerReadService(
            AnswerRepository repository,
            AnswerMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
        logger.info("AnswerReadService initialized.");
    }
    
}

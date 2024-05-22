package com.nniett.kikaishin.app.service.crud.question;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.QuestionMapper;
import com.nniett.kikaishin.app.service.dto.QuestionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReadService
        extends ReadService
        <
                QuestionEntity,
                Integer,
                QuestionDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionReadService.class);

    public QuestionReadService(
            QuestionRepository repository,
            QuestionMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
        logger.info("QuestionReadService initialized.");
    }
    
}

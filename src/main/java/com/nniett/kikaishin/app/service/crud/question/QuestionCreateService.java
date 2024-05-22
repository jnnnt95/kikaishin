package com.nniett.kikaishin.app.service.crud.question;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.QuestionMapper;
import com.nniett.kikaishin.app.service.mapper.dto.question.QuestionCreationMapper;
import com.nniett.kikaishin.app.service.dto.QuestionDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionCreationDto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionCreateService
        extends CreateService
        <
                QuestionEntity,
                Integer,
                QuestionDto,
                QuestionCreationDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionCreateService.class);

    public QuestionCreateService(
            QuestionRepository repository,
            QuestionMapper entityPojoMapper,
            @Qualifier("questionCreationMapperImpl")
            QuestionCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
        logger.info("QuestionCreateService initialized.");
    }

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public void populateAsDefaultForCreation(QuestionEntity pojo) {
        logger.debug("Populating default fields for new question.");
        logger.trace("Populating default field active as true.");
        pojo.setActive(true);
    }
}

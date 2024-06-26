package com.nniett.kikaishin.app.service.crud.answer;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.AnswerMapper;
import com.nniett.kikaishin.app.service.mapper.dto.answer.AnswerCreationMapper;
import com.nniett.kikaishin.app.service.dto.AnswerDto;
import com.nniett.kikaishin.app.service.dto.write.answer.AnswerCreationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerCreateService
        extends CreateService
        <
                AnswerEntity,
                Integer,
                AnswerDto,
                AnswerCreationDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(AnswerCreateService.class);

    public AnswerCreateService(
            AnswerRepository repository,
            AnswerMapper entityPojoMapper,
            @Qualifier("answerCreationMapperImpl")
            AnswerCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
        logger.info("AnswerCreateService initialized.");
    }

    @Override
    public void populateAsDefaultForCreation(AnswerEntity entity) {}
}

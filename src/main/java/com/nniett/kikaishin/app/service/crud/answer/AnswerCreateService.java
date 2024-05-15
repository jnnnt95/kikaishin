package com.nniett.kikaishin.app.service.crud.answer;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.AnswerMapper;
import com.nniett.kikaishin.app.service.mapper.dto.answer.AnswerCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Answer;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerCreationDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerCreateService
        extends CreateService
        <
                AnswerEntity,
                Integer,
                Answer,
                AnswerCreationDto
                >
{

    public AnswerCreateService(
            AnswerRepository repository,
            AnswerMapper entityPojoMapper,
            @Qualifier("answerCreationMapperImpl")
            AnswerCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
    }

    @Override
    public void populateAsDefaultForCreation(AnswerEntity entity) {

    }
}

package com.nniett.kikaishin.app.service.crud.answer;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.service.construction.UpdateService;
import com.nniett.kikaishin.app.service.mapper.AnswerMapper;
import com.nniett.kikaishin.app.service.mapper.dto.answer.AnswerUpdateMapper;
import com.nniett.kikaishin.app.service.pojo.Answer;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerUpdateDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerUpdateService
        extends UpdateService
        <
                AnswerEntity,
                Integer,
                AnswerUpdateDto,
                Answer
                >
{

    public AnswerUpdateService(
            AnswerRepository repository,
            AnswerMapper entityPojoMapper,
            @Qualifier("answerUpdateMapperImpl")
            AnswerUpdateMapper updateMapper
    ) {
        super(repository, entityPojoMapper, updateMapper);
    }

    @Override
    public void populateEntityForUpdate(AnswerEntity entity, Answer pojo) {
        if(pojo.getBody() != null &&
                !pojo.getBody().isEmpty() &&
                !pojo.getBody().equals(entity.getBody())) {
            entity.setBody(pojo.getBody());
        }
        if(pojo.getOrderIndex() != null &&
                pojo.getOrderIndex() > 0 &&
                !pojo.getOrderIndex().equals(entity.getOrderIndex())) {
            entity.setOrderIndex(pojo.getOrderIndex());
        }
    }
}

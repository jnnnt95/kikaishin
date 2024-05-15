package com.nniett.kikaishin.app.service.crud.question;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionRepository;
import com.nniett.kikaishin.app.service.construction.UpdateService;
import com.nniett.kikaishin.app.service.mapper.QuestionMapper;
import com.nniett.kikaishin.app.service.mapper.dto.question.QuestionUpdateMapper;
import com.nniett.kikaishin.app.service.pojo.Question;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionUpdateDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionUpdateService
        extends UpdateService
        <
                QuestionEntity,
                Integer,
                QuestionUpdateDto,
                Question
                >
{

    public QuestionUpdateService(
            QuestionRepository repository,
            QuestionMapper entityPojoMapper,
            @Qualifier("questionUpdateMapperImpl")
            QuestionUpdateMapper updateMapper
    ) {
        super(repository, entityPojoMapper, updateMapper);
    }

    @Override
    public void populateEntityForUpdate(QuestionEntity entity, Question pojo) {
        if(pojo.getBody() != null &&
                !pojo.getBody().isEmpty() &&
                !pojo.getBody().equals(entity.getBody())) {
            entity.setBody(pojo.getBody());
        }
        if(pojo.getActive() != null &&
                pojo.getActive() != entity.getActive()) {
            entity.setActive(pojo.getActive());
        }
    }
}

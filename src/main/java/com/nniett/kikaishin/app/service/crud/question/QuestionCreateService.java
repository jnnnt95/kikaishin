package com.nniett.kikaishin.app.service.crud.question;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionRepository;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.QuestionMapper;
import com.nniett.kikaishin.app.service.mapper.TopicMapper;
import com.nniett.kikaishin.app.service.mapper.dto.question.QuestionCreationMapper;
import com.nniett.kikaishin.app.service.mapper.dto.topic.TopicCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Question;
import com.nniett.kikaishin.app.service.pojo.Topic;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicCreationDto;
import com.nniett.kikaishin.common.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Repository
public class QuestionCreateService
        extends CreateService
        <
                QuestionEntity,
                Integer,
                Question,
                QuestionCreationDto
                >
{
    public QuestionCreateService(
            QuestionRepository repository,
            QuestionMapper entityPojoMapper,
            @Qualifier("questionCreationMapperImpl")
            QuestionCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
    }

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public void populateAsDefaultForCreation(QuestionEntity pojo) {
        pojo.setActive(true);
    }
}

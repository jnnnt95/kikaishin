package com.nniett.kikaishin.app.service.crud.question;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionRepository;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.QuestionMapper;
import com.nniett.kikaishin.app.service.mapper.TopicMapper;
import com.nniett.kikaishin.app.service.pojo.Question;
import com.nniett.kikaishin.app.service.pojo.Topic;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicUpdateDto;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReadService
        extends ReadService
        <
                QuestionEntity,
                Integer,
                Question
                >
{

    public QuestionReadService(
            QuestionRepository repository,
            QuestionMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }
    
}

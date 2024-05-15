package com.nniett.kikaishin.app.service.crud.question;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionRepository;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDeleteService
        extends DeleteService<QuestionEntity, Integer>
{

    public QuestionDeleteService(QuestionRepository repository) {
        super(repository);
    }

}

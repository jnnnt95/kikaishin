package com.nniett.kikaishin.app.service.crud.answer;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerDeleteService
        extends DeleteService<AnswerEntity, Integer>
{

    public AnswerDeleteService(AnswerRepository repository) {
        super(repository);
    }

}

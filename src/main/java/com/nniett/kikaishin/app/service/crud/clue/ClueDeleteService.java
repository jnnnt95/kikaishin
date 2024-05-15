package com.nniett.kikaishin.app.service.crud.clue;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.persistence.repository.ClueRepository;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.springframework.stereotype.Repository;

@Repository
public class ClueDeleteService
        extends DeleteService<ClueEntity, Integer>
{

    public ClueDeleteService(ClueRepository repository) {
        super(repository);
    }

}

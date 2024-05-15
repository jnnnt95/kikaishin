package com.nniett.kikaishin.app.service.crud.clue;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.persistence.repository.ClueRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.AnswerMapper;
import com.nniett.kikaishin.app.service.mapper.ClueMapper;
import com.nniett.kikaishin.app.service.pojo.Answer;
import com.nniett.kikaishin.app.service.pojo.Clue;
import org.springframework.stereotype.Repository;

@Repository
public class ClueReadService
        extends ReadService
        <
                ClueEntity,
                Integer,
                Clue
                >
{

    public ClueReadService(
            ClueRepository repository,
            ClueMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }
    
}

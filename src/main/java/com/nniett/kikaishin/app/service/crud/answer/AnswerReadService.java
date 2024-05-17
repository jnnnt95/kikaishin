package com.nniett.kikaishin.app.service.crud.answer;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.AnswerMapper;
import com.nniett.kikaishin.app.service.dto.AnswerDto;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerReadService
        extends ReadService
        <
                AnswerEntity,
                Integer,
                AnswerDto
                >
{

    public AnswerReadService(
            AnswerRepository repository,
            AnswerMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }
    
}

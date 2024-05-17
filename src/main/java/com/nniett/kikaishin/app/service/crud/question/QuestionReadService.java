package com.nniett.kikaishin.app.service.crud.question;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.repository.QuestionRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.QuestionMapper;
import com.nniett.kikaishin.app.service.dto.QuestionDto;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionReadService
        extends ReadService
        <
                QuestionEntity,
                Integer,
                QuestionDto
                >
{

    public QuestionReadService(
            QuestionRepository repository,
            QuestionMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }
    
}

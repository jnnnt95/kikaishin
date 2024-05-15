package com.nniett.kikaishin.app.service.crud.clue;

import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.persistence.repository.ClueRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.ClueMapper;
import com.nniett.kikaishin.app.service.mapper.dto.clue.ClueCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Clue;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueCreationDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ClueCreateService
        extends CreateService
        <
                ClueEntity,
                Integer,
                Clue,
                ClueCreationDto
                >
{

    public ClueCreateService(
            ClueRepository repository,
            ClueMapper entityPojoMapper,
            @Qualifier("clueCreationMapperImpl")
            ClueCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
    }

    @Override
    public void populateAsDefaultForCreation(ClueEntity entity) {}
}

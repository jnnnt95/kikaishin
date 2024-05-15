package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.persistence.repository.ClueRepository;
import com.nniett.kikaishin.app.service.construction.Service;
import com.nniett.kikaishin.app.service.crud.clue.ClueCreateService;
import com.nniett.kikaishin.app.service.crud.clue.ClueDeleteService;
import com.nniett.kikaishin.app.service.crud.clue.ClueReadService;
import com.nniett.kikaishin.app.service.crud.clue.ClueUpdateService;
import com.nniett.kikaishin.app.service.pojo.Clue;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClueService
        extends Service
        <
                ClueCreationDto,
                ClueUpdateDto,
                Clue,
                ClueEntity,
                Integer
                >
{

    @Autowired
    public ClueService(
            ClueRepository repository,
            ClueCreateService createService,
            ClueReadService readService,
            ClueUpdateService updateService,
            ClueDeleteService deleteService
    ) {
        super(repository, createService, readService, updateService, deleteService);
    }

    @Override
    public void populateAsDefaultForCreation(ClueEntity entity) {

    }

    @Override
    public ClueEntity readEntity(Integer id) {
        return getRepository().findById(id).orElseThrow();
    }

    @Override
    public void populateEntityForUpdate(ClueEntity answerEntity, Clue pojo) {}

    public Integer countExistingIds(List<Integer> questionIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return ((ClueRepository) getRepository()).countByIdIn(username, questionIds);
    }

}
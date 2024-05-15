package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.service.construction.Service;
import com.nniett.kikaishin.app.service.crud.answer.AnswerCreateService;
import com.nniett.kikaishin.app.service.crud.answer.AnswerDeleteService;
import com.nniett.kikaishin.app.service.crud.answer.AnswerReadService;
import com.nniett.kikaishin.app.service.crud.answer.AnswerUpdateService;
import com.nniett.kikaishin.app.service.pojo.Answer;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerService
        extends Service
        <
                AnswerCreationDto,
                AnswerUpdateDto,
                Answer,
                AnswerEntity,
                Integer
                >
{

    @Autowired
    public AnswerService(
            AnswerRepository repository,
            AnswerCreateService createService,
            AnswerReadService readService,
            AnswerUpdateService updateService,
            AnswerDeleteService deleteService
    ) {
        super(repository, createService, readService, updateService, deleteService);
    }

    @Override
    public void populateAsDefaultForCreation(AnswerEntity entity) {

    }

    @Override
    public AnswerEntity readEntity(Integer id) {
        return getRepository().findById(id).orElseThrow();
    }

    @Override
    public void populateEntityForUpdate(AnswerEntity answerEntity, Answer pojo) {}

    public Integer countExistingIds(List<Integer> questionIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return ((AnswerRepository) getRepository()).countByIdIn(username, questionIds);
    }

}
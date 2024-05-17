package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.repository.AnswerRepository;
import com.nniett.kikaishin.app.service.construction.Service;
import com.nniett.kikaishin.app.service.crud.answer.AnswerCreateService;
import com.nniett.kikaishin.app.service.crud.answer.AnswerDeleteService;
import com.nniett.kikaishin.app.service.crud.answer.AnswerReadService;
import com.nniett.kikaishin.app.service.crud.answer.AnswerUpdateService;
import com.nniett.kikaishin.app.service.dto.AnswerDto;
import com.nniett.kikaishin.app.service.dto.write.answer.AnswerCreationDto;
import com.nniett.kikaishin.app.service.dto.write.answer.AnswerUpdateDto;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerService
        extends Service
        <
                AnswerCreationDto,
                AnswerUpdateDto,
                AnswerDto,
                AnswerEntity,
                Integer
                >
        implements UsesHttpServletRequest, CanRetrieveUsernameFromJWT
{
    private final JwtUtils jwtUtils;

    @Autowired
    public AnswerService(
            AnswerRepository repository,
            AnswerCreateService createService,
            AnswerReadService readService,
            AnswerUpdateService updateService,
            AnswerDeleteService deleteService,
            JwtUtils jwtUtils
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void populateAsDefaultForCreation(AnswerEntity entity) {

    }

    @Override
    public AnswerEntity readEntity(Integer id) {
        return getRepository().findById(id).orElseThrow();
    }

    @Override
    public void populateEntityForUpdate(AnswerEntity answerEntity, AnswerDto pojo) {}

    public Integer countExistingIds(List<Integer> questionIds) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return ((AnswerRepository) getRepository()).countByIdIn(username, questionIds);
    }

}
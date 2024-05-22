package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.persistence.repository.ClueRepository;
import com.nniett.kikaishin.app.service.construction.Service;
import com.nniett.kikaishin.app.service.crud.clue.ClueCreateService;
import com.nniett.kikaishin.app.service.crud.clue.ClueDeleteService;
import com.nniett.kikaishin.app.service.crud.clue.ClueReadService;
import com.nniett.kikaishin.app.service.crud.clue.ClueUpdateService;
import com.nniett.kikaishin.app.service.dto.ClueDto;
import com.nniett.kikaishin.app.service.dto.write.clue.ClueCreationDto;
import com.nniett.kikaishin.app.service.dto.write.clue.ClueUpdateDto;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClueService
        extends Service
        <
                ClueCreationDto,
                ClueUpdateDto,
                ClueDto,
                ClueEntity,
                Integer
                >
        implements UsesHttpServletRequest, CanRetrieveUsernameFromJWT
{
    private static final Logger logger = LoggerFactory.getLogger(ClueService.class);

    private final JwtUtils jwtUtils;

    @Autowired
    public ClueService(
            ClueRepository repository,
            ClueCreateService createService,
            ClueReadService readService,
            ClueUpdateService updateService,
            ClueDeleteService deleteService,
            JwtUtils jwtUtils
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.jwtUtils = jwtUtils;
        logger.info("ClueService initialized.");
    }

    @Override
    public void populateAsDefaultForCreation(ClueEntity entity) {

    }

    @Override
    public ClueEntity readEntity(Integer id) {
        return getRepository().findById(id).orElseThrow();
    }

    @Override
    public void populateEntityForUpdate(ClueEntity answerEntity, ClueDto pojo) {}

    public Integer countExistingIds(List<Integer> questionIds) {
        logger.debug("Retrieving count of clues by ids.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Counting clues by ids for username {}.", username);
        return ((ClueRepository) getRepository()).countByIdIn(username, questionIds);
    }

}
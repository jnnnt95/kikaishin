package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.persistence.repository.ShelfRepository;
import com.nniett.kikaishin.app.persistence.repository.virtual.ShelfInfoVirtualRepository;
import com.nniett.kikaishin.app.service.construction.*;
import com.nniett.kikaishin.app.service.crud.shelf.ShelfCreateService;
import com.nniett.kikaishin.app.service.crud.shelf.ShelfDeleteService;
import com.nniett.kikaishin.app.service.crud.shelf.ShelfReadService;
import com.nniett.kikaishin.app.service.crud.shelf.ShelfUpdateService;
import com.nniett.kikaishin.app.service.dto.ShelfDto;
import com.nniett.kikaishin.app.service.dto.ShelfInfoDto;
import com.nniett.kikaishin.app.service.mapper.ShelfInfoMapper;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfCreationDto;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfUpdateDto;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ShelfService
        extends ActivateableService
        <
                ShelfEntity,
                Integer,
                ShelfDto,
                ShelfCreationDto,
                ShelfUpdateDto
                >
        implements UsesHttpServletRequest, CanRetrieveUsernameFromJWT
{

    private final ShelfInfoVirtualRepository shelfInfoRepository;
    private final ShelfInfoMapper shelfInfoMapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public ShelfService(
            ShelfRepository repository,
            ShelfCreateService createService,
            ShelfReadService readService,
            ShelfUpdateService updateService,
            ShelfDeleteService deleteService,
            ShelfInfoVirtualRepository shelfInfoRepository,
            ShelfInfoMapper shelfInfoMapper,
            JwtUtils jwtUtils
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.shelfInfoRepository = shelfInfoRepository;
        this.shelfInfoMapper = shelfInfoMapper;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void populateAsDefaultForCreation(ShelfEntity entity) {
        getCreateService().populateAsDefaultForCreation(entity);
    }

    @Override
    public void populateEntityForUpdate(ShelfEntity entity, ShelfDto shelf) {
        getUpdateService().populateEntityForUpdate(entity, shelf);
    }

    @Override
    public ShelfEntity findEntityByDto(ShelfUpdateDto shelfUpdateDto) {
        return getRepository().findById(shelfUpdateDto.getShelfId()).orElseThrow();
    }

    public ShelfDto getShelfById(int id) {
        return readPojo(id);
    }

    @Override
    public ListCrudRepository<ShelfEntity, Integer> getActivateableRepository() {
        return getRepository();
    }


    public ShelfInfoDto getShelfInfo(Integer shelfId) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return shelfInfoMapper.
                toShelfInfo(
                        shelfInfoRepository.
                                getShelvesInfoById(username, Collections.singletonList(shelfId)).get(0)
                );
    }

    public List<ShelfInfoDto> getShelvesInfo(List<Integer> shelfIds) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return shelfInfoMapper.toShelvesInfo(shelfInfoRepository.getShelvesInfoById(username, shelfIds));
    }

    public Integer countExistingIds(List<Integer> bookIds) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return ((ShelfRepository) getRepository()).countByIdIn(username, bookIds);
    }

}
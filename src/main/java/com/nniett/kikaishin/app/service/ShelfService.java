package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.persistence.repository.ShelfRepository;
import com.nniett.kikaishin.app.persistence.repository.virtual.ShelfInfoVirtualRepository;
import com.nniett.kikaishin.app.service.construction.*;
import com.nniett.kikaishin.app.service.crud.shelf.ShelfCreateService;
import com.nniett.kikaishin.app.service.crud.shelf.ShelfDeleteService;
import com.nniett.kikaishin.app.service.crud.shelf.ShelfReadService;
import com.nniett.kikaishin.app.service.crud.shelf.ShelfUpdateService;
import com.nniett.kikaishin.app.service.mapper.ShelfInfoMapper;
import com.nniett.kikaishin.app.service.pojo.Shelf;
import com.nniett.kikaishin.app.service.pojo.ShelfInfo;
import com.nniett.kikaishin.app.service.pojo.dto.shelf.ShelfCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.shelf.ShelfUpdateDto;
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
                Shelf,
                ShelfCreationDto,
                ShelfUpdateDto
                >
{

    private final ShelfInfoVirtualRepository shelfInfoRepository;
    private final ShelfInfoMapper shelfInfoMapper;

    @Autowired
    public ShelfService(
            ShelfRepository repository,
            ShelfCreateService createService,
            ShelfReadService readService,
            ShelfUpdateService updateService,
            ShelfDeleteService deleteService,
            ShelfInfoVirtualRepository shelfInfoRepository,
            ShelfInfoMapper shelfInfoMapper
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.shelfInfoRepository = shelfInfoRepository;
        this.shelfInfoMapper = shelfInfoMapper;
    }

    @Override
    public void populateAsDefaultForCreation(ShelfEntity entity) {
        getCreateService().populateAsDefaultForCreation(entity);
    }

    @Override
    public void populateEntityForUpdate(ShelfEntity entity, Shelf shelf) {
        getUpdateService().populateEntityForUpdate(entity, shelf);
    }

    @Override
    public ShelfEntity findEntityByDto(ShelfUpdateDto shelfUpdateDto) {
        return getRepository().findById(shelfUpdateDto.getShelfId()).orElseThrow();
    }

    public Shelf getShelfById(int id) {
        return readPojo(id);
    }

    @Override
    public ListCrudRepository<ShelfEntity, Integer> getActivateableRepository() {
        return getRepository();
    }


    public ShelfInfo getShelfInfo(Integer shelfId) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return shelfInfoMapper.
                toShelfInfo(
                        shelfInfoRepository.
                                getShelvesInfoById(username, Collections.singletonList(shelfId)).get(0)
                );
    }

    public List<ShelfInfo> getShelvesInfo(List<Integer> shelfIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return shelfInfoMapper.toShelvesInfo(shelfInfoRepository.getShelvesInfoById(username, shelfIds));
    }

    public Integer countExistingIds(List<Integer> bookIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return ((ShelfRepository) getRepository()).countByIdIn(username, bookIds);
    }

}
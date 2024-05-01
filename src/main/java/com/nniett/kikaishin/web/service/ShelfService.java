package com.nniett.kikaishin.web.service;

import com.nniett.kikaishin.web.persistence.entity.BookEntity;
import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.web.persistence.repository.ShelfRepository;
import com.nniett.kikaishin.web.service.construction.*;
import com.nniett.kikaishin.web.service.crud.book.BookCreateService;
import com.nniett.kikaishin.web.service.crud.shelf.ShelfCreateService;
import com.nniett.kikaishin.web.service.crud.shelf.ShelfDeleteService;
import com.nniett.kikaishin.web.service.crud.shelf.ShelfReadService;
import com.nniett.kikaishin.web.service.crud.shelf.ShelfUpdateService;
import com.nniett.kikaishin.web.service.pojo.Book;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookCreationDto;
import com.nniett.kikaishin.web.service.pojo.dto.shelf.ShelfCreationDto;
import com.nniett.kikaishin.web.service.pojo.dto.shelf.ShelfUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfService
        extends ActivateableWithChildrenService
        <
                        ShelfEntity,
                        Integer,
                        Shelf,
                        ShelfCreationDto,
                        ShelfUpdateDto,
                        BookEntity,
                        Integer,
                        Book,
                        BookCreationDto
                        >
{

    @Autowired
    public ShelfService(
            ShelfRepository repository,
            ShelfCreateService createService,
            ShelfReadService readService,
            ShelfUpdateService updateService,
            ShelfDeleteService deleteService,
            BookCreateService childService
    ) {
        super(repository, createService, readService, updateService, deleteService, childService);
    }

    @Override
    public void populateAsDefaultPojoForCreation(Shelf shelf) {
        shelf.setActive(true);
        //TODO: after user login impl, hardcoded 1 should change
        shelf.setUsername("1");
    }

    @Override
    public void populateEntityForUpdate(ShelfEntity entity, Shelf shelf) {
        getUpdateService().populateEntityForUpdate(entity, shelf);
    }

    @Override
    public ShelfEntity findEntityByDto(ShelfUpdateDto shelfUpdateDto) {
        return getRepository().findById(shelfUpdateDto.getShelfId()).get();
    }

    public Shelf getShelfById(int id) {
        return read(id);
    }

    @Override
    public ListCrudRepository<ShelfEntity, Integer> getActivateableRepository() {
        return getRepository();
    }

}
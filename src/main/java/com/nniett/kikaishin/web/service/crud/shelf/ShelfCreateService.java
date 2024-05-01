package com.nniett.kikaishin.web.service.crud.shelf;

import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.web.service.construction.CreateService;
import com.nniett.kikaishin.web.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.web.service.mapper.ShelfMapper;
import com.nniett.kikaishin.web.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.web.service.mapper.dto.shelf.ShelfCreationMapper;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import com.nniett.kikaishin.web.service.pojo.dto.shelf.ShelfCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfCreateService
        extends CreateService
        <
                ShelfEntity,
                Integer,
                Shelf,
                ShelfCreationDto
                >
{
    @Autowired
    public ShelfCreateService(
            ListCrudRepository<ShelfEntity, Integer> repository,
            ShelfMapper entityPojoMapper,
            ShelfCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
    }

    @Override
    public void populateAsDefaultPojoForCreation(Shelf shelf) {
        shelf.setActive(true);
    }
}

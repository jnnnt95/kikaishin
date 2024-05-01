package com.nniett.kikaishin.web.service.crud.shelf;

import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.web.service.construction.CreateService;
import com.nniett.kikaishin.web.service.construction.ReadService;
import com.nniett.kikaishin.web.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.web.service.mapper.ShelfMapper;
import com.nniett.kikaishin.web.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import com.nniett.kikaishin.web.service.pojo.dto.shelf.ShelfCreationDto;
import com.nniett.kikaishin.web.service.pojo.dto.shelf.ShelfUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfReadService
        extends ReadService
        <
                ShelfEntity,
                ShelfUpdateDto,
                Integer,
                Shelf
                >
{

    public ShelfReadService(
            ListCrudRepository<ShelfEntity, Integer> repository,
            ShelfMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }

}

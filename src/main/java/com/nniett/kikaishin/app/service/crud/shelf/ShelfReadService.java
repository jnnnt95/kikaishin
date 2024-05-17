package com.nniett.kikaishin.app.service.crud.shelf;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.ShelfMapper;
import com.nniett.kikaishin.app.service.dto.ShelfDto;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfReadService
        extends ReadService
        <
                ShelfEntity,
                Integer,
                ShelfDto
                >
{

    public ShelfReadService(
            ListCrudRepository<ShelfEntity, Integer> repository,
            ShelfMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }

}

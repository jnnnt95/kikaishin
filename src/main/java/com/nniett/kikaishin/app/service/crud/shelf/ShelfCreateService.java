package com.nniett.kikaishin.app.service.crud.shelf;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.service.UserService;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.ShelfMapper;
import com.nniett.kikaishin.app.service.mapper.dto.shelf.ShelfCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Shelf;
import com.nniett.kikaishin.app.service.pojo.dto.shelf.ShelfCreationDto;
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
    private final UserService userService;

    @Autowired
    public ShelfCreateService(
            ListCrudRepository<ShelfEntity, Integer> repository,
            ShelfMapper entityPojoMapper,
            @Qualifier("shelfCreationMapperImpl")
            ShelfCreationMapper createMapper,
            UserService userService
    ) {
        super(repository, entityPojoMapper, createMapper);
        this.userService = userService;
    }

    @Override
    public void populateAsDefaultForCreation(ShelfEntity entity) {
        //TODO: after user login impl, hardcoded 1 should change
        entity.setUser(userService.getReadService().getRepository().findById("1").get());
        entity.setUserId(entity.getUser().getUsername());
        entity.setActive(true);
    }
}

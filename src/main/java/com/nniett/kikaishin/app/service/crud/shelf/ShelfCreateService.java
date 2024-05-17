package com.nniett.kikaishin.app.service.crud.shelf;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.service.UserService;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.dto.ShelfDto;
import com.nniett.kikaishin.app.service.mapper.ShelfMapper;
import com.nniett.kikaishin.app.service.mapper.dto.shelf.ShelfCreationMapper;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfCreationDto;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
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
                ShelfDto,
                ShelfCreationDto
                >
        implements UsesHttpServletRequest, CanRetrieveUsernameFromJWT
{
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public ShelfCreateService(
            ListCrudRepository<ShelfEntity, Integer> repository,
            ShelfMapper entityPojoMapper,
            @Qualifier("shelfCreationMapperImpl")
            ShelfCreationMapper createMapper,
            UserService userService,
            JwtUtils jwtUtils
    ) {
        super(repository, entityPojoMapper, createMapper);
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void populateAsDefaultForCreation(ShelfEntity entity) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        entity.setUser(userService.getReadService().getRepository().findById(username).orElseThrow());
        entity.setUserId(entity.getUser().getUsername());
        entity.setActive(true);
    }
}

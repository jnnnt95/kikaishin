package com.nniett.kikaishin.app.service.crud.shelf;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.service.UserService;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.dto.ShelfDto;
import com.nniett.kikaishin.app.service.mapper.ShelfMapper;
import com.nniett.kikaishin.app.service.mapper.dto.shelf.ShelfCreationMapper;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfCreationDto;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    private static final Logger logger = LoggerFactory.getLogger(ShelfCreateService.class);

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
        logger.info("ShelfCreateService initialized.");
    }

    @Override
    public void populateAsDefaultForCreation(ShelfEntity entity) {
        logger.debug("Populating default fields for new shelf.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Populating logged user's username as {}.", username);
        logger.debug("Retrieving user using provided username.");
        Optional<UserEntity> userCtnr = userService.getReadService().getRepository().findById(username);
        if(userCtnr.isPresent()) {
            entity.setUser(userCtnr.get());
            entity.setUserId(entity.getUser().getUsername());
        } else {
            logger.error("User with username '{}' not found.", username);
            throw new RuntimeException("Couldn't find user");
        }
        logger.trace("Populating default field active as true.");
        entity.setActive(true);
    }
}

package com.nniett.kikaishin.app.service.crud.review;

import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.ReviewRepository;
import com.nniett.kikaishin.app.service.UserService;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.dto.ReviewDto;
import com.nniett.kikaishin.app.service.mapper.ReviewMapper;
import com.nniett.kikaishin.app.service.mapper.dto.review.ReviewCreationMapper;
import com.nniett.kikaishin.app.service.dto.write.review.ReviewCreationDto;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReviewCreateService
        extends CreateService
        <
                ReviewEntity,
                Integer,
                ReviewDto,
                ReviewCreationDto
                >
    implements UsesHttpServletRequest, CanRetrieveUsernameFromJWT
{
    private static final Logger logger = LoggerFactory.getLogger(ReviewCreateService.class);

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public ReviewCreateService(
            ReviewRepository repository,
            ReviewMapper entityPojoMapper,
            @Qualifier("reviewCreationMapperImpl")
            ReviewCreationMapper createMapper,
            UserService userService,
            JwtUtils jwtUtils
    ) {
        super(repository, entityPojoMapper, createMapper);
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        logger.info("ReviewCreateService initialized.");
    }

    @Override
    public void populateAsDefaultForCreation(ReviewEntity entity) {
        logger.debug("Populating default fields for new review.");
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
    }
}

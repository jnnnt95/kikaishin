package com.nniett.kikaishin.app.service.crud.review;

import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.ReviewRepository;
import com.nniett.kikaishin.app.service.UserService;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.ReviewMapper;
import com.nniett.kikaishin.app.service.mapper.dto.review.ReviewCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Review;
import com.nniett.kikaishin.app.service.pojo.dto.review.ReviewCreationDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReviewCreateService
        extends CreateService
        <
                ReviewEntity,
                Integer,
                Review,
                ReviewCreationDto
                >
{
    private final UserService userService;

    public ReviewCreateService(
            ReviewRepository repository,
            ReviewMapper entityPojoMapper,
            @Qualifier("reviewCreationMapperImpl")
            ReviewCreationMapper createMapper,
            UserService userService
    ) {
        super(repository, entityPojoMapper, createMapper);
        this.userService = userService;
    }

    @Override
    public void populateAsDefaultForCreation(ReviewEntity entity) {
        //TODO: after user login impl, hardcoded 1 should change
        Optional<UserEntity> userCtnr = userService.getReadService().getRepository().findById("1");
        if(userCtnr.isPresent()) {
            entity.setUser(userCtnr.get());
            entity.setUserId(entity.getUser().getUsername());
        } else {
            throw new RuntimeException("Couldn't find user");
        }
    }
}

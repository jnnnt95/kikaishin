package com.nniett.kikaishin.app.service.crud.review;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.persistence.repository.ReviewRepository;
import com.nniett.kikaishin.app.service.UserService;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.BookMapper;
import com.nniett.kikaishin.app.service.mapper.ReviewMapper;
import com.nniett.kikaishin.app.service.mapper.dto.book.BookCreationMapper;
import com.nniett.kikaishin.app.service.mapper.dto.review.ReviewCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Book;
import com.nniett.kikaishin.app.service.pojo.Review;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.review.ReviewCreationDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

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
        entity.setUser(userService.getReadService().getRepository().findById("1").get());
        entity.setUserId(entity.getUser().getUsername());
    }
}

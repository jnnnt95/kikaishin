package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.persistence.repository.BookRepository;
import com.nniett.kikaishin.app.persistence.repository.ReviewRepository;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.service.construction.ActivateableService;
import com.nniett.kikaishin.app.service.construction.ImmutableService;
import com.nniett.kikaishin.app.service.crud.book.BookCreateService;
import com.nniett.kikaishin.app.service.crud.book.BookDeleteService;
import com.nniett.kikaishin.app.service.crud.book.BookReadService;
import com.nniett.kikaishin.app.service.crud.book.BookUpdateService;
import com.nniett.kikaishin.app.service.crud.review.ReviewCreateService;
import com.nniett.kikaishin.app.service.crud.review.ReviewDeleteService;
import com.nniett.kikaishin.app.service.crud.review.ReviewReadService;
import com.nniett.kikaishin.app.service.pojo.Book;
import com.nniett.kikaishin.app.service.pojo.Review;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.review.ReviewCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewService
        extends ImmutableService
        <
                ReviewCreationDto,
                Review,
                ReviewEntity,
                Integer
                >
{

    @Autowired
    public ReviewService(
            ReviewRepository repository,
            ReviewCreateService createService,
            ReviewReadService readService,
            ReviewDeleteService deleteService
    ) {
        super(repository, createService, readService, deleteService);
    }

    @Override
    public void populateAsDefaultForCreation(ReviewEntity entity) {

    }

    @Override
    public ReviewEntity readEntity(Integer id) {
        return getRepository().findById(id).get();
    }

    public Integer countExistingIds(List<Integer> reviewIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return ((ReviewRepository) getRepository()).countByIdIn(username, reviewIds);
    }
}
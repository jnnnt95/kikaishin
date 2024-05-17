package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.persistence.repository.ReviewRepository;
import com.nniett.kikaishin.app.service.construction.ImmutableService;
import com.nniett.kikaishin.app.service.crud.review.ReviewCreateService;
import com.nniett.kikaishin.app.service.crud.review.ReviewDeleteService;
import com.nniett.kikaishin.app.service.crud.review.ReviewReadService;
import com.nniett.kikaishin.app.service.dto.ReviewDto;
import com.nniett.kikaishin.app.service.dto.write.review.ReviewCreationDto;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewService
        extends ImmutableService
        <
                ReviewCreationDto,
                ReviewDto,
                ReviewEntity,
                Integer
                >
        implements UsesHttpServletRequest, CanRetrieveUsernameFromJWT
{
    private final JwtUtils jwtUtils;

    @Autowired
    public ReviewService(
            ReviewRepository repository,
            ReviewCreateService createService,
            ReviewReadService readService,
            ReviewDeleteService deleteService,
            JwtUtils jwtUtils
    ) {
        super(repository, createService, readService, deleteService);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void populateAsDefaultForCreation(ReviewEntity entity) {

    }

    @Override
    public ReviewEntity readEntity(Integer id) {
        return getRepository().findById(id).orElseThrow();
    }

    public Integer countExistingIds(List<Integer> reviewIds) {
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        return ((ReviewRepository) getRepository()).countByIdIn(username, reviewIds);
    }
}
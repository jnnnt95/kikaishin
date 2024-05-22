package com.nniett.kikaishin.app.service.crud.review;

import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.persistence.repository.ReviewRepository;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDeleteService
        extends DeleteService<ReviewEntity, Integer>
{
    private static final Logger logger = LoggerFactory.getLogger(ReviewDeleteService.class);

    public ReviewDeleteService(ReviewRepository repository) {
        super(repository);
        logger.info("ReviewDeleteService initialized.");
    }

}

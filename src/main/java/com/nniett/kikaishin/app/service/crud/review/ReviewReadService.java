package com.nniett.kikaishin.app.service.crud.review;

import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.persistence.repository.ReviewRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.dto.ReviewDto;
import com.nniett.kikaishin.app.service.mapper.ReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewReadService
        extends ReadService
        <
                ReviewEntity,
                Integer,
                ReviewDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(ReviewReadService.class);

    public ReviewReadService(
            ReviewRepository repository,
            ReviewMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
        logger.info("ReviewReadService initialized.");
    }
    
}

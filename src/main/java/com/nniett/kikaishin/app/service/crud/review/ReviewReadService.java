package com.nniett.kikaishin.app.service.crud.review;

import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.persistence.repository.ReviewRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.dto.ReviewDto;
import com.nniett.kikaishin.app.service.mapper.ReviewMapper;
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

    public ReviewReadService(
            ReviewRepository repository,
            ReviewMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }
    
}

package com.nniett.kikaishin.app.service.crud.book;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.BookMapper;
import com.nniett.kikaishin.app.service.dto.BookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookReadService
        extends ReadService
        <
                BookEntity,
                Integer,
                BookDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(BookReadService.class);

    public BookReadService(
            ListCrudRepository<BookEntity, Integer> repository,
            BookMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
        logger.debug("BookReadService initialized.");
    }
    
}

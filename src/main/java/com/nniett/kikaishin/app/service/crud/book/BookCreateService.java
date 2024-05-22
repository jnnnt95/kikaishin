package com.nniett.kikaishin.app.service.crud.book;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.BookMapper;
import com.nniett.kikaishin.app.service.mapper.dto.book.BookCreationMapper;
import com.nniett.kikaishin.app.service.dto.BookDto;
import com.nniett.kikaishin.app.service.dto.write.book.BookCreationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookCreateService
        extends CreateService
        <
                BookEntity,
                Integer,
                BookDto,
                BookCreationDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(BookCreateService.class);

    public BookCreateService(
            ListCrudRepository<BookEntity, Integer> repository,
            BookMapper entityPojoMapper,
            @Qualifier("bookCreationMapperImpl")
            BookCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
        logger.info("BookCreateService initialized.");
    }

    @Override
    public void populateAsDefaultForCreation(BookEntity entity) {
        logger.debug("Populating default fields for new book.");
        logger.trace("Populating default field active as true.");
        entity.setActive(true);
    }
}

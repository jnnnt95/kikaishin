package com.nniett.kikaishin.app.service.crud.book;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.BookMapper;
import com.nniett.kikaishin.app.service.mapper.dto.book.BookCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Book;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookCreationDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookCreateService
        extends CreateService
        <
                BookEntity,
                Integer,
                Book,
                BookCreationDto
                >
{

    public BookCreateService(
            ListCrudRepository<BookEntity, Integer> repository,
            BookMapper entityPojoMapper,
            @Qualifier("bookCreationMapperImpl")
            BookCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
    }

    @Override
    public void populateAsDefaultForCreation(BookEntity entity) {
        entity.setActive(true);
    }
}

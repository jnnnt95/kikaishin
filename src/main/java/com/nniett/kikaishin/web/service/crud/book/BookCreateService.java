package com.nniett.kikaishin.web.service.crud.book;

import com.nniett.kikaishin.web.persistence.entity.BookEntity;
import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.web.service.construction.CreateService;
import com.nniett.kikaishin.web.service.mapper.BookMapper;
import com.nniett.kikaishin.web.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.web.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.web.service.mapper.dto.book.BookCreationMapper;
import com.nniett.kikaishin.web.service.pojo.Book;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookCreationDto;
import com.nniett.kikaishin.web.service.pojo.dto.shelf.ShelfCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
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
            BookCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
    }

    @Override
    public void populateAsDefaultPojoForCreation(Book book) {
        book.setActive(true);
    }
}

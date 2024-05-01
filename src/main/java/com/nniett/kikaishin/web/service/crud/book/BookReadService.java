package com.nniett.kikaishin.web.service.crud.book;

import com.nniett.kikaishin.web.persistence.entity.BookEntity;
import com.nniett.kikaishin.web.service.construction.ReadService;
import com.nniett.kikaishin.web.service.mapper.BookMapper;
import com.nniett.kikaishin.web.service.mapper.EntityPojoMapper;
import com.nniett.kikaishin.web.service.pojo.Book;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookUpdateDto;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookReadService
        extends ReadService
        <
                BookEntity,
                BookUpdateDto,
                Integer,
                Book
                >
{

    public BookReadService(
            ListCrudRepository<BookEntity, Integer> repository,
            BookMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }
    
}

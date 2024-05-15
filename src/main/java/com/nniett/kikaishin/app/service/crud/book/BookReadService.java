package com.nniett.kikaishin.app.service.crud.book;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.BookMapper;
import com.nniett.kikaishin.app.service.pojo.Book;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookUpdateDto;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookReadService
        extends ReadService
        <
                BookEntity,
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

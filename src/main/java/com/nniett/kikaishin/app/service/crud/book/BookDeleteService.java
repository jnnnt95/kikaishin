package com.nniett.kikaishin.app.service.crud.book;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookDeleteService
        extends DeleteService<BookEntity, Integer>
{

    public BookDeleteService(ListCrudRepository<BookEntity, Integer> repository) {
        super(repository);
    }

}

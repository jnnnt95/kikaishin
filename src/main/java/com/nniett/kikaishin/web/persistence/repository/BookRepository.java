package com.nniett.kikaishin.web.persistence.repository;

import com.nniett.kikaishin.web.persistence.entity.BookEntity;
import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface BookRepository extends ListCrudRepository<BookEntity, Integer> {
}

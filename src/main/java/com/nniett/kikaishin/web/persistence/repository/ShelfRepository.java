package com.nniett.kikaishin.web.persistence.repository;

import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ShelfRepository extends ListCrudRepository<ShelfEntity, Integer> {
}

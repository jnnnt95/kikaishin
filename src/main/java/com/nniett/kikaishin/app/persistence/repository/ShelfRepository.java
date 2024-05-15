package com.nniett.kikaishin.app.persistence.repository;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.nniett.kikaishin.common.SqlConstants.GET___BOOK_COUNT___QUERY;
import static com.nniett.kikaishin.common.SqlConstants.GET___SHELF_COUNT___QUERY;

public interface ShelfRepository extends ListCrudRepository<ShelfEntity, Integer> {

    @Query(value = GET___SHELF_COUNT___QUERY, nativeQuery = true)
    Integer countByIdIn(
            @Param("username") String username,
            @Param("ids") List<Integer> ids
    );

}

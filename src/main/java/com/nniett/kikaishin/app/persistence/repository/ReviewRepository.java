package com.nniett.kikaishin.app.persistence.repository;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.nniett.kikaishin.common.SqlConstants.GET___REVIEW_COUNT___QUERY;
import static com.nniett.kikaishin.common.SqlConstants.GET___TOPIC_COUNT___QUERY;

public interface ReviewRepository extends ListCrudRepository<ReviewEntity, Integer> {

    @Query(value = GET___REVIEW_COUNT___QUERY, nativeQuery = true)
    Integer countByIdIn(
            @Param("username") String username,
            @Param("ids") List<Integer> ids
    );

}

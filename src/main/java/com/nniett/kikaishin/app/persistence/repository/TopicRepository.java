package com.nniett.kikaishin.app.persistence.repository;

import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.nniett.kikaishin.common.SqlConstants.GET___TOPIC_COUNT___QUERY;

public interface TopicRepository extends ListCrudRepository<TopicEntity, Integer> {

    @QueryHints(value = {@QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT")})
    List<TopicEntity> findByLookupKey(String l);

    @Query(value = GET___TOPIC_COUNT___QUERY, nativeQuery = true)
    Integer countByIdIn(
            @Param("username") String username,
            @Param("ids") List<Integer> ids
    );

}

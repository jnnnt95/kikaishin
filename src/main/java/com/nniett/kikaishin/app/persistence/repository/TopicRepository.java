package com.nniett.kikaishin.app.persistence.repository;

import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.nniett.kikaishin.common.SqlConstants.GET___QUESTION_COUNT___QUERY;
import static com.nniett.kikaishin.common.SqlConstants.GET___TOPIC_COUNT___QUERY;

public interface TopicRepository extends ListCrudRepository<TopicEntity, Integer> {

//    @Query(value = "select topic from TopicEntity topic where topic.lookupKey = :lookupKey")
//    TopicEntity lookupKeyExists(@Param("lookupKey") String lookupKey);

    @QueryHints(value = {@QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT")})
    //@QueryHints(value = {@QueryHint(name = "org.hibernate.FlushMode", value = "COMMIT")})
    List<TopicEntity> findByLookupKey(String l);

    @Query(value = GET___TOPIC_COUNT___QUERY, nativeQuery = true)
    Integer countByIdIn(
            @Param("username") String username,
            @Param("ids") List<Integer> ids
    );

}

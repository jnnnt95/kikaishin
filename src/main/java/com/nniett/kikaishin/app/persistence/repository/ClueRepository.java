package com.nniett.kikaishin.app.persistence.repository;

import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.nniett.kikaishin.common.SqlConstants.GET___CLUE_COUNT___QUERY;

public interface ClueRepository extends ListCrudRepository<ClueEntity, Integer> {

    List<ClueEntity> findByQuestionId(Integer questionId);

    @Query(value = GET___CLUE_COUNT___QUERY, nativeQuery = true)
    Integer countByIdIn(
            @Param("username") String username,
            @Param("ids") List<Integer> ids
    );
}

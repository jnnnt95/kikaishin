package com.nniett.kikaishin.app.persistence.repository;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.nniett.kikaishin.common.SqlConstants.*;

public interface QuestionRepository extends ListCrudRepository<QuestionEntity, Integer> {

    @Query(value = GET___QUESTION_COUNT___QUERY, nativeQuery = true)
    Integer countByIdIn(
            @Param("username") String username,
            @Param("ids") List<Integer> ids
    );

    @Query(value = GET___USER_QUESTIONS___QUERY, nativeQuery = true)
    List<QuestionEntity> findByUsername(
            @Param("username") String username
    );

    List<QuestionEntity> findAllByIdIn(List<Integer> ids);

}

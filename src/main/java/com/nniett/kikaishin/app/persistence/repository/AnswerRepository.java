package com.nniett.kikaishin.app.persistence.repository;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.nniett.kikaishin.common.SqlConstants.*;

public interface AnswerRepository extends ListCrudRepository<AnswerEntity, Integer> {

    List<AnswerEntity> findByQuestionId(Integer questionId);

    @Query(value = GET___ANSWER_COUNT___QUERY, nativeQuery = true)
    Integer countByIdIn(
            @Param("username") String username,
            @Param("ids") List<Integer> ids
    );

}

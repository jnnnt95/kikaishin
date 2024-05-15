package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.virtual.QuestionInfoVirtualEntity;
import com.nniett.kikaishin.app.persistence.entity.virtual.TopicInfoVirtualEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nniett.kikaishin.common.SqlConstants.*;
import static com.nniett.kikaishin.app.persistence.entity.virtual.GenericUtils.*;

@Repository
public class QuestionInfoVirtualRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<QuestionInfoVirtualEntity> getTopicQuestionsInfo(Integer topicId) {
        return this.jdbcTemplate.query(
                GET___TOPIC_QUESTIONS_INFO___QUERY,
                new BeanPropertyRowMapper<>(QuestionInfoVirtualEntity.class),
                topicId
        );
    }

    public List<QuestionInfoVirtualEntity> getQuestionsInfoById(String username, List<Integer> questionIds) {
        String query = buildQueryWithPreparationList(GET___QUESTIONS_INFO___QUERY, 1, questionIds.size());
        Object[] params = new Object[1 + questionIds.size()];
        params[0] = username;
        for (int i = 0; i < questionIds.size(); i++) {
            params[i + 1] = questionIds.get(i);
        }

        List<QuestionInfoVirtualEntity> questionsInfo = this.jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(QuestionInfoVirtualEntity.class),
                params
        );

        questionsInfo.forEach(QuestionInfoVirtualEntity::calculate);

        return questionsInfo;
    }

}

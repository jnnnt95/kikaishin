package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.virtual.QuestionInfoVirtualEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nniett.kikaishin.common.SqlConstants.*;
import static com.nniett.kikaishin.app.persistence.entity.virtual.GenericUtils.*;

@Repository
public class QuestionInfoVirtualRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuestionInfoVirtualRepository.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("QuestionInfoVirtualRepository initialized.");
    }

    public List<QuestionInfoVirtualEntity> getTopicQuestionsInfo(Integer topicId) {
        logger.debug("Retrieving questions info to feed parent info entity.");
        logger.trace("Used query: {}.", GET___TOPIC_QUESTIONS_INFO___QUERY);
        logger.trace("Parent topic id: {}.", topicId);
        List<QuestionInfoVirtualEntity> questionInfoVirtualEntities =  this.jdbcTemplate.query(
                GET___TOPIC_QUESTIONS_INFO___QUERY,
                new BeanPropertyRowMapper<>(QuestionInfoVirtualEntity.class),
                topicId
        );
        logger.debug("QuestionInfoVirtualEntity list formed successfully.");
        return questionInfoVirtualEntities;
    }

    public List<QuestionInfoVirtualEntity> getQuestionsInfoById(String username, List<Integer> questionIds) {
        logger.debug("Retrieving questions info from request ids list.");
        logger.trace("Questions info being retrieved for username: {}.", username);
        logger.trace("Questions ids to retrieve info: {}.", questionIds);
        logger.trace("Used query: {}.", GET___QUESTIONS_INFO___QUERY);
        String query = buildQueryWithPreparationList(GET___QUESTIONS_INFO___QUERY, 1, questionIds.size());
        logger.trace("Processed query: {}.", query);
        Object[] params = new Object[1 + questionIds.size()];
        params[0] = username;
        logger.trace("Query params in 0: {}.", params[0]);
        for (int i = 0; i < questionIds.size(); i++) {
            params[i + 1] = questionIds.get(i);
            logger.trace("Query params in {}: {}.", i + 1, params[i + 1]);
        }

        logger.debug("Running query using injected JdbcTemplate.");
        List<QuestionInfoVirtualEntity> questionsInfo = this.jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(QuestionInfoVirtualEntity.class),
                params
        );
        logger.debug("QuestionInfoVirtualEntity list formed successfully.");

        logger.debug("Calculating entities info.");
        questionsInfo.forEach(QuestionInfoVirtualEntity::calculate);

        return questionsInfo;
    }

}

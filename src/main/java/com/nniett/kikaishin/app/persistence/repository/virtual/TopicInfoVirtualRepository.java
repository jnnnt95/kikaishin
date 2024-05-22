package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.virtual.QuestionInfoVirtualEntity;
import com.nniett.kikaishin.app.persistence.entity.virtual.TopicInfoVirtualEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.nniett.kikaishin.app.persistence.entity.virtual.GenericUtils.buildQueryWithPreparationList;
import static com.nniett.kikaishin.common.SqlConstants.*;

@Component
public class TopicInfoVirtualRepository {

    private static final Logger logger = LoggerFactory.getLogger(TopicInfoVirtualRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final QuestionInfoVirtualRepository questionInfoRepository;

    @Autowired
    public TopicInfoVirtualRepository(JdbcTemplate jdbcTemplate, QuestionInfoVirtualRepository questionInfoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.questionInfoRepository = questionInfoRepository;
        logger.info("TopicInfoVirtualRepository initialized.");
    }

    public List<TopicInfoVirtualEntity> getTopicsInfoById(String username, List<Integer> topicsIds) {
        logger.debug("Retrieving topics info from ids list.");
        logger.trace("Topics info being retrieved for username: {}.", username);
        logger.trace("Topic ids to retrieve info: {}.", topicsIds);
        logger.trace("Used query: {}.", GET___TOPICS_INFO___QUERY);
        String query = buildQueryWithPreparationList(GET___TOPICS_INFO___QUERY, 1, topicsIds.size());
        logger.trace("Processed query: {}.", query);
        Object[] params = new Object[1 + topicsIds.size()];
        params[0] = username;
        logger.trace("Query params in 0: {}.", params[0]);
        for (int i = 0; i < topicsIds.size(); i++) {
            params[i + 1] = topicsIds.get(i);
            logger.trace("Query params in {}: {}.", i + 1, params[i + 1]);
        }

        logger.debug("Running query using injected JdbcTemplate.");
        List<TopicInfoVirtualEntity> topicsInfo = this.jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(TopicInfoVirtualEntity.class),
                params
        );
        logger.debug("BookInfoVirtualEntity list formed successfully.");

        logger.debug("Initializing child for retrieving relevant entities info.");
        topicsInfo.forEach(t -> t.setQuestionsInfo(questionInfoRepository.getTopicQuestionsInfo(t.getTopicId())));
        logger.debug("Calculating children question info entities.");
        topicsInfo.forEach(t -> t.getQuestionsInfo().forEach(QuestionInfoVirtualEntity::calculate));
        logger.debug("Calculating entities info.");
        topicsInfo.forEach(TopicInfoVirtualEntity::calculate);

        return topicsInfo;
    }



    public List<TopicInfoVirtualEntity> getBookTopicsInfo(Integer bookId) {
        logger.debug("Retrieving topics info to feed parent info entity.");
        logger.trace("Used query: {}.", GET___BOOK_TOPICS_INFO___QUERY);
        logger.trace("Parent book id: {}.", bookId);
        List<TopicInfoVirtualEntity> topicsInfo = this.jdbcTemplate.query(
                GET___BOOK_TOPICS_INFO___QUERY,
                new BeanPropertyRowMapper<>(TopicInfoVirtualEntity.class),
                bookId
        );
        logger.debug("TopicInfoVirtualEntity list formed successfully.");
        logger.debug("Initializing child for retrieving relevant entity info.");
        topicsInfo.forEach(t -> t.setQuestionsInfo(questionInfoRepository.getTopicQuestionsInfo(t.getTopicId())));
        logger.debug("Calculating children question info entities.");
        topicsInfo.forEach(t -> t.getQuestionsInfo().forEach(QuestionInfoVirtualEntity::calculate));
        logger.debug("Calculating entity info.");
        topicsInfo.forEach(TopicInfoVirtualEntity::calculate);

        return topicsInfo;
    }
}

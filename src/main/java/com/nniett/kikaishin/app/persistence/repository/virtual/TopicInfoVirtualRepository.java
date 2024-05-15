package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.virtual.QuestionInfoVirtualEntity;
import com.nniett.kikaishin.app.persistence.entity.virtual.TopicInfoVirtualEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.nniett.kikaishin.app.persistence.entity.virtual.GenericUtils.buildQueryWithPreparationList;
import static com.nniett.kikaishin.common.SqlConstants.*;

@Component
public class TopicInfoVirtualRepository {

    private final JdbcTemplate jdbcTemplate;
    private final QuestionInfoVirtualRepository questionInfoRepository;

    @Autowired
    public TopicInfoVirtualRepository(JdbcTemplate jdbcTemplate, QuestionInfoVirtualRepository questionInfoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.questionInfoRepository = questionInfoRepository;
    }

    public List<TopicInfoVirtualEntity> getTopicsInfoById(String username, List<Integer> topicsIds) {
        String query = buildQueryWithPreparationList(GET___TOPICS_INFO___QUERY, 1, topicsIds.size());
        Object[] params = new Object[1 + topicsIds.size()];
        params[0] = username;
        for (int i = 0; i < topicsIds.size(); i++) {
            params[i + 1] = topicsIds.get(i);
        }

        List<TopicInfoVirtualEntity> topicsInfo = this.jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(TopicInfoVirtualEntity.class),
                params
        );

        topicsInfo.forEach(t -> t.setQuestionsInfo(questionInfoRepository.getTopicQuestionsInfo(t.getTopicId())));
        topicsInfo.forEach(t -> t.getQuestionsInfo().forEach(QuestionInfoVirtualEntity::calculate));
        topicsInfo.forEach(TopicInfoVirtualEntity::calculate);

        return topicsInfo;
    }



    public List<TopicInfoVirtualEntity> getBookTopicsInfo(Integer bookId) {
        List<TopicInfoVirtualEntity> topicsInfo = this.jdbcTemplate.query(
                GET___BOOK_TOPICS_INFO___QUERY,
                new BeanPropertyRowMapper<>(TopicInfoVirtualEntity.class),
                bookId
        );

        topicsInfo.forEach(t -> t.setQuestionsInfo(questionInfoRepository.getTopicQuestionsInfo(t.getTopicId())));
        topicsInfo.forEach(t -> t.getQuestionsInfo().forEach(QuestionInfoVirtualEntity::calculate));
        topicsInfo.forEach(TopicInfoVirtualEntity::calculate);

        return topicsInfo;
    }
}

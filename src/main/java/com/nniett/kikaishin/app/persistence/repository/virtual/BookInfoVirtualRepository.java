package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.virtual.BookInfoVirtualEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.nniett.kikaishin.app.persistence.entity.virtual.GenericUtils.buildQueryWithPreparationList;
import static com.nniett.kikaishin.common.SqlConstants.*;

@Component
public class BookInfoVirtualRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TopicInfoVirtualRepository topicInfoRepository;

    @Autowired
    public BookInfoVirtualRepository(JdbcTemplate jdbcTemplate, TopicInfoVirtualRepository topicInfoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.topicInfoRepository = topicInfoRepository;
    }

    public List<BookInfoVirtualEntity> getBooksInfoById(String username, List<Integer> booksIds) {
        String query = buildQueryWithPreparationList(GET___BOOKS_INFO___QUERY, 1, booksIds.size());
        Object[] params = new Object[1 + booksIds.size()];
        params[0] = username;
        for (int i = 0; i < booksIds.size(); i++) {
            params[i + 1] = booksIds.get(i);
        }

        List<BookInfoVirtualEntity> booksInfo = this.jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(BookInfoVirtualEntity.class),
                params
        );

        booksInfo.forEach(b -> b.setTopicsInfo(topicInfoRepository.getBookTopicsInfo(b.getBookId())));
        booksInfo.forEach(BookInfoVirtualEntity::calculate);

        return booksInfo;
    }

    public List<BookInfoVirtualEntity> getShelfBooksInfo(Integer shelfId) {
        List<BookInfoVirtualEntity> booksInfo = this.jdbcTemplate.query(
                GET___SHELF_BOOKS_INFO___QUERY,
                new BeanPropertyRowMapper<>(BookInfoVirtualEntity.class),
                shelfId
        );

        booksInfo.forEach(b -> b.setTopicsInfo(topicInfoRepository.getBookTopicsInfo(b.getBookId())));
        booksInfo.forEach(BookInfoVirtualEntity::calculate);

        return booksInfo;
    }
}

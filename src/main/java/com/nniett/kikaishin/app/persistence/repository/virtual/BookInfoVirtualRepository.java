package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.virtual.BookInfoVirtualEntity;
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
public class BookInfoVirtualRepository {

    private static final Logger logger = LoggerFactory.getLogger(BookInfoVirtualRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final TopicInfoVirtualRepository topicInfoRepository;

    @Autowired
    public BookInfoVirtualRepository(JdbcTemplate jdbcTemplate, TopicInfoVirtualRepository topicInfoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.topicInfoRepository = topicInfoRepository;
        logger.info("BookInfoVirtualRepository initialized.");
    }

    public List<BookInfoVirtualEntity> getBooksInfoById(String username, List<Integer> booksIds) {
        logger.debug("Retrieving books info from ids list.");
        logger.trace("Books info being retrieved for username: {}.", username);
        logger.trace("Book ids to retrieve info: {}.", booksIds);
        logger.trace("Used query: {}.", GET___BOOKS_INFO___QUERY);
        String query = buildQueryWithPreparationList(GET___BOOKS_INFO___QUERY, 1, booksIds.size());
        logger.trace("Processed query: {}.", query);
        Object[] params = new Object[1 + booksIds.size()];
        params[0] = username;
        logger.trace("Query params in 0: {}.", params[0]);
        for (int i = 0; i < booksIds.size(); i++) {
            params[i + 1] = booksIds.get(i);
            logger.trace("Query params in {}: {}.", i + 1, params[i + 1]);
        }

        logger.debug("Running query using injected JdbcTemplate.");
        List<BookInfoVirtualEntity> booksInfo = this.jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(BookInfoVirtualEntity.class),
                params
        );
        logger.debug("BookInfoVirtualEntity list formed successfully.");

        logger.debug("Initializing child for retrieving relevant entities info.");
        booksInfo.forEach(b -> b.setTopicsInfo(topicInfoRepository.getBookTopicsInfo(b.getBookId())));
        logger.debug("Calculating entities info.");
        booksInfo.forEach(BookInfoVirtualEntity::calculate);

        return booksInfo;
    }

    public List<BookInfoVirtualEntity> getShelfBooksInfo(Integer shelfId) {
        logger.debug("Retrieving books info to feed parent info entity.");
        logger.trace("Used query: {}.", GET___SHELF_BOOKS_INFO___QUERY);
        logger.trace("Parent shelf id: {}.", shelfId);
        List<BookInfoVirtualEntity> booksInfo = this.jdbcTemplate.query(
                GET___SHELF_BOOKS_INFO___QUERY,
                new BeanPropertyRowMapper<>(BookInfoVirtualEntity.class),
                shelfId
        );
        logger.debug("BookInfoVirtualEntity list formed successfully.");
        logger.debug("Initializing child for retrieving relevant entity info.");
        booksInfo.forEach(b -> b.setTopicsInfo(topicInfoRepository.getBookTopicsInfo(b.getBookId())));
        logger.debug("Calculating entity info.");
        booksInfo.forEach(BookInfoVirtualEntity::calculate);

        return booksInfo;
    }
}

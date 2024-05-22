package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.virtual.ShelfInfoVirtualEntity;
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
public class ShelfInfoVirtualRepository {

    private static final Logger logger = LoggerFactory.getLogger(ShelfInfoVirtualRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final BookInfoVirtualRepository bookInfoRepository;

    @Autowired
    public ShelfInfoVirtualRepository(JdbcTemplate jdbcTemplate, BookInfoVirtualRepository bookInfoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookInfoRepository = bookInfoRepository;
        logger.info("ShelfInfoVirtualRepository initialized.");
    }

    public List<ShelfInfoVirtualEntity> getShelvesInfoById(String username, List<Integer> shelfIds) {
        logger.debug("Retrieving shelves info from ids list.");
        logger.trace("Shelves info being retrieved for username: {}.", username);
        logger.trace("Shelf ids to retrieve info: {}.", shelfIds);
        logger.trace("Used query: {}.", GET___SHELVES_INFO___QUERY);
        String query = buildQueryWithPreparationList(GET___SHELVES_INFO___QUERY, 1, shelfIds.size());
        logger.trace("Processed query: {}.", query);
        Object[] params = new Object[1 + shelfIds.size()];
        params[0] = username;
        logger.trace("Query params in 0: {}.", params[0]);
        for (int i = 0; i < shelfIds.size(); i++) {
            params[i + 1] = shelfIds.get(i);
            logger.trace("Query params in {}: {}.", i + 1, params[i + 1]);
        }

        logger.debug("Running query using injected JdbcTemplate.");
        List<ShelfInfoVirtualEntity> shelvesInfo = this.jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(ShelfInfoVirtualEntity.class),
                params
        );
        logger.debug("ShelfInfoVirtualEntity list formed successfully.");

        logger.debug("Initializing child for retrieving relevant entities info.");
        shelvesInfo.forEach(s -> s.setBooksInfo(bookInfoRepository.getShelfBooksInfo(s.getShelfId())));
        logger.debug("Calculating entities info.");
        shelvesInfo.forEach(ShelfInfoVirtualEntity::calculate);

        return shelvesInfo;
    }

    public List<ShelfInfoVirtualEntity> getUserShelvesInfo(String username) {
        logger.debug("Retrieving shelves info to feed parent info entity.");
        logger.trace("Used query: {}.", GET___USER_SHELVES_INFO___QUERY);
        logger.trace("Parent user's username: {}.", username);
        List<ShelfInfoVirtualEntity> shelvesInfo = this.jdbcTemplate.query(
                GET___USER_SHELVES_INFO___QUERY,
                new BeanPropertyRowMapper<>(ShelfInfoVirtualEntity.class),
                username
        );
        logger.debug("ShelfInfoVirtualEntity list formed successfully.");
        logger.debug("Initializing child for retrieving relevant entity info.");
        shelvesInfo.forEach(s -> s.setBooksInfo(bookInfoRepository.getShelfBooksInfo(s.getShelfId())));
        logger.debug("Calculating entity info.");
        shelvesInfo.forEach(ShelfInfoVirtualEntity::calculate);

        return shelvesInfo;
    }
}

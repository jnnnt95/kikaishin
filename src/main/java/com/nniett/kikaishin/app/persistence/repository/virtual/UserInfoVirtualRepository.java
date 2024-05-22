package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.virtual.UserInfoVirtualEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.nniett.kikaishin.common.SqlConstants.GET___USER_INFO___QUERY;

@Component
public class UserInfoVirtualRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoVirtualRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final ShelfInfoVirtualRepository shelfInfoRepository;

    @Autowired
    public UserInfoVirtualRepository(JdbcTemplate jdbcTemplate, ShelfInfoVirtualRepository shelfInfoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.shelfInfoRepository = shelfInfoRepository;
        logger.info("UserInfoVirtualRepository initialized.");
    }

    public UserInfoVirtualEntity getUserInfoById(String username) {
        logger.debug("Retrieving user info.");
        logger.trace("User info being retrieved for username: {}.", username);
        logger.trace("Used query: {}.", GET___USER_INFO___QUERY);
        UserInfoVirtualEntity userInfo = this.jdbcTemplate.query(
                GET___USER_INFO___QUERY,
                new BeanPropertyRowMapper<>(UserInfoVirtualEntity.class),
                username
        ).get(0);
        logger.debug("UserInfoVirtualEntity formed successfully.");

        logger.debug("Initializing child for retrieving relevant entity info.");
        userInfo.setShelvesInfo(shelfInfoRepository.getUserShelvesInfo(userInfo.getUsername()));
        logger.debug("Calculating entity info.");
        userInfo.calculate();

        return userInfo;
    }
}

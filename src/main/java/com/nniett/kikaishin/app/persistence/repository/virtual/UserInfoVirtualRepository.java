package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.virtual.UserInfoVirtualEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.nniett.kikaishin.common.SqlConstants.GET___USER_INFO___QUERY;

@Component
public class UserInfoVirtualRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ShelfInfoVirtualRepository shelfInfoRepository;

    @Autowired
    public UserInfoVirtualRepository(JdbcTemplate jdbcTemplate, ShelfInfoVirtualRepository shelfInfoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.shelfInfoRepository = shelfInfoRepository;
    }

    public UserInfoVirtualEntity getUserInfoById(String username) {
        UserInfoVirtualEntity userInfo = this.jdbcTemplate.query(
                GET___USER_INFO___QUERY,
                new BeanPropertyRowMapper<>(UserInfoVirtualEntity.class),
                username
        ).get(0);

        userInfo.setShelvesInfo(shelfInfoRepository.getUserShelvesInfo(userInfo.getUsername()));
        userInfo.calculate();

        return userInfo;
    }
}

package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.entity.virtual.ShelfInfoVirtualEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.nniett.kikaishin.app.persistence.entity.virtual.GenericUtils.buildQueryWithPreparationList;
import static com.nniett.kikaishin.common.SqlConstants.*;

@Component
public class ShelfInfoVirtualRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BookInfoVirtualRepository bookInfoRepository;

    @Autowired
    public ShelfInfoVirtualRepository(JdbcTemplate jdbcTemplate, BookInfoVirtualRepository bookInfoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookInfoRepository = bookInfoRepository;
    }

    public List<ShelfInfoVirtualEntity> getShelvesInfoById(String username, List<Integer> shelfIds) {
        String query = buildQueryWithPreparationList(GET___SHELVES_INFO___QUERY, 1, shelfIds.size());
        Object[] params = new Object[1 + shelfIds.size()];
        params[0] = username;
        for (int i = 0; i < shelfIds.size(); i++) {
            params[i + 1] = shelfIds.get(i);
        }

        List<ShelfInfoVirtualEntity> shelvesInfo = this.jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(ShelfInfoVirtualEntity.class),
                params
        );

        shelvesInfo.forEach(s -> s.setBooksInfo(bookInfoRepository.getShelfBooksInfo(s.getShelfId())));
        shelvesInfo.forEach(ShelfInfoVirtualEntity::calculate);

        return shelvesInfo;
    }

    public List<ShelfInfoVirtualEntity> getUserShelvesInfo(String username) {
        List<ShelfInfoVirtualEntity> shelvesInfo = this.jdbcTemplate.query(
                GET___USER_SHELVES_INFO___QUERY,
                new BeanPropertyRowMapper<>(ShelfInfoVirtualEntity.class),
                username
        );

        shelvesInfo.forEach(s -> s.setBooksInfo(bookInfoRepository.getShelfBooksInfo(s.getShelfId())));
        shelvesInfo.forEach(ShelfInfoVirtualEntity::calculate);

        return shelvesInfo;
    }
}

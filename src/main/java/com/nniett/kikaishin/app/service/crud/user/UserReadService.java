package com.nniett.kikaishin.app.service.crud.user;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.UserRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.UserMapper;
import com.nniett.kikaishin.app.service.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserReadService
        extends ReadService
        <
                UserEntity,
                String,
                UserDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(UserReadService.class);

    public UserReadService(
            UserRepository repository,
            UserMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
        logger.info("UserReadService initialized.");
    }

}

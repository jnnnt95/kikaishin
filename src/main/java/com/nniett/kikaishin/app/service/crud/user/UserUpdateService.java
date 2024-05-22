package com.nniett.kikaishin.app.service.crud.user;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.UserRepository;
import com.nniett.kikaishin.app.service.construction.UpdateService;
import com.nniett.kikaishin.app.service.dto.UserDto;
import com.nniett.kikaishin.app.service.mapper.UserMapper;
import com.nniett.kikaishin.app.service.mapper.dto.user.UserUpdateMapper;
import com.nniett.kikaishin.app.service.dto.write.user.UserUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserUpdateService
        extends UpdateService
        <
                UserEntity,
                String,
                UserUpdateDto,
                UserDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(UserUpdateService.class);

    public UserUpdateService(
            UserRepository repository,
            UserMapper entityPojoMapper,
            @Qualifier("userUpdateMapperImpl")
            UserUpdateMapper updateMapper
    ) {
        super(repository, entityPojoMapper, updateMapper);
        logger.info("UserUpdateService initialized.");
    }

    @Override
    public void populateEntityForUpdate(UserEntity entity, UserDto pojo) {}
}

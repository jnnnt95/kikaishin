package com.nniett.kikaishin.app.service.crud.user;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.UserRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.mapper.UserMapper;
import com.nniett.kikaishin.app.service.mapper.dto.user.UserCreationMapper;
import com.nniett.kikaishin.app.service.pojo.User;
import com.nniett.kikaishin.app.service.pojo.dto.user.UserCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserCreateService
        extends CreateService
        <
                UserEntity,
                String,
                User,
                UserCreationDto
                >
{
    @Autowired
    public UserCreateService(
            UserRepository repository,
            UserMapper entityPojoMapper,
            @Qualifier("userCreationMapperImpl")
            UserCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
    }

    @Override
    public void populateAsDefaultForCreation(UserEntity pojo) {

    }
}

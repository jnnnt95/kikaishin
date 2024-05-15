package com.nniett.kikaishin.app.service.crud.user;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.UserRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.UserMapper;
import com.nniett.kikaishin.app.service.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserReadService
        extends ReadService
        <
                UserEntity,
                String,
                User
                >
{

    public UserReadService(
            UserRepository repository,
            UserMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }

}

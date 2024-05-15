package com.nniett.kikaishin.app.service.crud.user;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.persistence.repository.UserRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.ShelfMapper;
import com.nniett.kikaishin.app.service.mapper.UserMapper;
import com.nniett.kikaishin.app.service.pojo.Shelf;
import com.nniett.kikaishin.app.service.pojo.User;
import com.nniett.kikaishin.app.service.pojo.dto.shelf.ShelfUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.user.UserUpdateDto;
import org.springframework.data.repository.ListCrudRepository;
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

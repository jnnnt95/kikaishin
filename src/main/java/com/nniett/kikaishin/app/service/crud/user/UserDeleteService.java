package com.nniett.kikaishin.app.service.crud.user;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserDeleteService
        extends DeleteService<UserEntity, String>
{

    public UserDeleteService(ListCrudRepository<UserEntity, String> repository) {
        super(repository);
    }

}

package com.nniett.kikaishin.app.service.crud.user;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserDeleteService
        extends DeleteService<UserEntity, String>
{
    private static final Logger logger = LoggerFactory.getLogger(UserDeleteService.class);

    public UserDeleteService(ListCrudRepository<UserEntity, String> repository) {
        super(repository);
        logger.info("UserDeleteService initialized.");
    }

}

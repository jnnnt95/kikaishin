package com.nniett.kikaishin.web.persistence.repository;

import com.nniett.kikaishin.web.persistence.entity.UserEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<UserEntity, String> {

}

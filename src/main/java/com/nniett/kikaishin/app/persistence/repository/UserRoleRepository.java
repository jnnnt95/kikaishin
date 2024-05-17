package com.nniett.kikaishin.app.persistence.repository;

import com.nniett.kikaishin.app.persistence.entity.id.UserRoleEntityPK;
import com.nniett.kikaishin.app.persistence.entity.UserRoleEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRoleRepository extends ListCrudRepository<UserRoleEntity, UserRoleEntityPK> {

}

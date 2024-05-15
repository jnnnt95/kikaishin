package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.service.pojo.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ShelfMapper.class})
public interface UserMapper  extends EntityPojoMapper<UserEntity, User> {
    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "displayName", target = "displayName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "shelves", target = "shelves"),
            @Mapping(target = "PK", ignore = true),
            @Mapping(target = "children", ignore = true)
    })
    User toPojo(UserEntity entity);
    @Override
    List<User> toPojos(List<UserEntity> entities);

    @InheritInverseConfiguration
    UserEntity toEntity(User entity);
    @Override
    List<UserEntity> toEntities(List<User> entities);
}

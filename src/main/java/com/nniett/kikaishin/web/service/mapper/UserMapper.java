package com.nniett.kikaishin.web.service.mapper;

import com.nniett.kikaishin.web.persistence.entity.UserEntity;
import com.nniett.kikaishin.web.service.pojo.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ShelfMapper.class})
public interface UserMapper {
    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "displayName", target = "displayName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "shelves", target = "shelves")
    })
    User toPojo(UserEntity entity);
    List<User> toPojos(List<UserEntity> entities);

    @InheritInverseConfiguration
    UserEntity toEntity(User entity);
    List<UserEntity> toEntities(List<User> entities);
}

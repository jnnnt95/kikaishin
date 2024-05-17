package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.UserEntity;
import com.nniett.kikaishin.app.service.dto.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ShelfMapper.class})
public interface UserMapper  extends EntityPojoMapper<UserEntity, UserDto> {
    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "displayName", target = "displayName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "shelves", target = "shelves"),
            @Mapping(target = "PK", ignore = true),
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "createDate", ignore = true)
    })
    UserDto toPojo(UserEntity entity);
    @Override
    List<UserDto> toPojos(List<UserEntity> entities);

    @InheritInverseConfiguration
    UserEntity toEntity(UserDto entity);
    @Override
    List<UserEntity> toEntities(List<UserDto> entities);
}

package com.nniett.kikaishin.app.service.mapper.dto.user;

import com.nniett.kikaishin.app.service.dto.UserDto;
import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.dto.write.user.UserCreationDto;
import com.nniett.kikaishin.app.service.mapper.dto.shelf.ShelfCreationMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {ShelfCreationMapper.class})
public interface UserCreationMapper extends DtoPojoMapper<UserCreationDto, UserDto> {
    @Override
    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "displayName", target = "displayName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "shelves", target = "shelves"),

            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true)
    })
    UserDto toPojo(UserCreationDto dto);

    @Override
    @InheritInverseConfiguration
    UserCreationDto toDto(UserDto entity);
}

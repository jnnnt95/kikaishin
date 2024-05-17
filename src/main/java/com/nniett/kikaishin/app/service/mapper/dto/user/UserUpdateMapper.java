package com.nniett.kikaishin.app.service.mapper.dto.user;


import com.nniett.kikaishin.app.service.dto.ShelfDto;
import com.nniett.kikaishin.app.service.dto.UserDto;
import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfUpdateDto;
import com.nniett.kikaishin.app.service.dto.write.user.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserUpdateMapper extends DtoPojoMapper<UserUpdateDto, UserDto> {
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "parentPK", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "books", ignore = true)
    ShelfDto toPojo(ShelfUpdateDto dto);
}
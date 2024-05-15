package com.nniett.kikaishin.app.service.mapper.dto.user;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.pojo.Shelf;
import com.nniett.kikaishin.app.service.pojo.User;
import com.nniett.kikaishin.app.service.pojo.dto.shelf.ShelfUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.user.UserUpdateDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserUpdateMapper extends DtoPojoMapper<UserUpdateDto, User> {
    Shelf toPojo(ShelfUpdateDto dto);
}
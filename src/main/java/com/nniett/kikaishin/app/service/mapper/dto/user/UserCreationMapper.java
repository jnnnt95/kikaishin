package com.nniett.kikaishin.app.service.mapper.dto.user;

import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.book.BookCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Shelf;
import com.nniett.kikaishin.app.service.pojo.User;
import com.nniett.kikaishin.app.service.pojo.dto.shelf.ShelfCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.user.UserCreationDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {BookCreationMapper.class})
public interface UserCreationMapper extends DtoPojoMapper<UserCreationDto, User> {
    Shelf toPojo(ShelfCreationDto dto);

    ShelfCreationDto toDto(Shelf entity);
}

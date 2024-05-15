package com.nniett.kikaishin.app.service.mapper.dto.shelf;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.book.BookCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Shelf;
import com.nniett.kikaishin.app.service.pojo.dto.shelf.ShelfCreationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {BookCreationMapper.class})
public interface ShelfCreationMapper extends DtoPojoMapper<ShelfCreationDto, Shelf> {
    @Override
    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "books", target = "books"),


            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "shelfId", ignore = true),
            @Mapping(target = "username", ignore = true),
            @Mapping(target = "active", ignore = true)
    })
    public Shelf toPojo(ShelfCreationDto dto);

    @Override
    @InheritInverseConfiguration
    public ShelfCreationDto toDto(Shelf entity);
}

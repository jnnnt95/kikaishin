package com.nniett.kikaishin.web.service.mapper.dto.shelf;


import com.nniett.kikaishin.web.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.web.service.mapper.dto.book.BookCreationMapper;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import com.nniett.kikaishin.web.service.pojo.dto.shelf.ShelfCreationDto;
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
            @Mapping(target = "shelfId", ignore = true),
            @Mapping(target = "username", ignore = true),
            @Mapping(target = "active", ignore = true),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    public Shelf toPojo(ShelfCreationDto dto);

    @Override
    @InheritInverseConfiguration
    public ShelfCreationDto toDto(Shelf entity);
}

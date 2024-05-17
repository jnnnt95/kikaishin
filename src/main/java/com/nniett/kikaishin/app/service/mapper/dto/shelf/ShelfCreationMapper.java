package com.nniett.kikaishin.app.service.mapper.dto.shelf;


import com.nniett.kikaishin.app.service.dto.ShelfDto;
import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.book.BookCreationMapper;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfCreationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {BookCreationMapper.class})
public interface ShelfCreationMapper extends DtoPojoMapper<ShelfCreationDto, ShelfDto> {
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
            @Mapping(target = "active", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    ShelfDto toPojo(ShelfCreationDto dto);

    @Override
    @InheritInverseConfiguration
    ShelfCreationDto toDto(ShelfDto entity);
}

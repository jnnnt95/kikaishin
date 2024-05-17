package com.nniett.kikaishin.app.service.mapper.dto.shelf;


import com.nniett.kikaishin.app.service.dto.ShelfDto;
import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ShelfUpdateMapper extends DtoPojoMapper<ShelfUpdateDto, ShelfDto> {
    @Mappings({
            @Mapping(source = "name", target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(source = "description", target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "shelfId", target = "shelfId"),

            @Mapping(target = "books", ignore = true),
            @Mapping(target = "username", ignore = true),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    ShelfDto toPojo(ShelfUpdateDto dto);
}
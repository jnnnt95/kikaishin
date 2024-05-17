package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.app.service.dto.ShelfDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class})
public interface ShelfMapper extends EntityPojoMapper<ShelfEntity, ShelfDto> {

    @Mappings({
            @Mapping(source = "id", target = "shelfId"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "updateDate", target = "updateDate"),
            @Mapping(source = "creationDate", target = "creationDate"),
            @Mapping(source = "books", target = "books"),
            @Mapping(target = "PK", ignore = true),
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "parentPK", ignore = true),
            @Mapping(target = "username", ignore = true)
    })
    @Mapping(source = "user", target = "user", ignore = true)
    ShelfDto toPojo(ShelfEntity entity);
    @Override
    List<ShelfDto> toPojos(List<ShelfEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source = "user", target = "user", ignore = true)
    ShelfEntity toEntity(ShelfDto pojo);
    @Override
    List<ShelfEntity> toEntities(List<ShelfDto> pojos);
}

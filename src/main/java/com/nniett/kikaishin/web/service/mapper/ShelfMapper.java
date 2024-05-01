package com.nniett.kikaishin.web.service.mapper;

import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class})
public interface ShelfMapper extends EntityPojoMapper<ShelfEntity, Shelf>{

    @Mappings({
            @Mapping(source = "id", target = "shelfId"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "updateDate", target = "updateDate"),
            @Mapping(source = "creationDate", target = "creationDate"),
            @Mapping(source = "books", target = "books")
    })
    @Mapping(source = "user", target = "user", ignore = true)
    Shelf toPojo(ShelfEntity entity);
    List<Shelf> toPojos(List<ShelfEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source = "user", target = "user", ignore = true)
    ShelfEntity toEntity(Shelf pojo);
    List<ShelfEntity> toEntities(List<Shelf> pojos);
}

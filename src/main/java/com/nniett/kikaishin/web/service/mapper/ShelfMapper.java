package com.nniett.kikaishin.web.service.mapper;

import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ShelfMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active")
    })
    @Mapping(source = "user", target = "user", ignore = true)
    Shelf toPojo(ShelfEntity entity);
    List<Shelf> toPojos(List<ShelfEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source = "user", target = "user", ignore = true)
    ShelfEntity toEntity(Shelf pojo);
    List<ShelfEntity> toEntities(List<Shelf> pojos);
}

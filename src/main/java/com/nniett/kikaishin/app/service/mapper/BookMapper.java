package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.service.dto.BookDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ShelfMapper.class, TopicMapper.class})
public interface BookMapper extends EntityPojoMapper<BookEntity, BookDto>{

    @Mappings({
            @Mapping(source = "id", target = "bookId"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "updateDate", target = "updateDate"),
            @Mapping(source = "creationDate", target = "creationDate"),
            @Mapping(source = "topics", target = "topics"),
            @Mapping(target = "PK", ignore = true),
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    @Mapping(source = "shelf", target = "parentShelf", ignore = true)
    BookDto toPojo(BookEntity entity);
    @Override
    List<BookDto> toPojos(List<BookEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source = "parentShelf", target = "shelf", ignore = true)
    BookEntity toEntity(BookDto pojo);
    @Override
    List<BookEntity> toEntities(List<BookDto> pojos);
}

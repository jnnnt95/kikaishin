package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.service.pojo.Book;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ShelfMapper.class, TopicMapper.class})
public interface BookMapper extends EntityPojoMapper<BookEntity, Book>{

    @Mappings({
            @Mapping(source = "id", target = "bookId"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "updateDate", target = "updateDate"),
            @Mapping(source = "creationDate", target = "creationDate"),
            @Mapping(source = "topics", target = "topics")
    })
    @Mapping(source = "shelf", target = "parentShelf", ignore = true)
    Book toPojo(BookEntity entity);
    @Override
    List<Book> toPojos(List<BookEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source = "parentShelf", target = "shelf", ignore = true)
    BookEntity toEntity(Book pojo);
    @Override
    List<BookEntity> toEntities(List<Book> pojos);
}

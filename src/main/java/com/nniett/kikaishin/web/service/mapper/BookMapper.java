package com.nniett.kikaishin.web.service.mapper;

import com.nniett.kikaishin.web.persistence.entity.BookEntity;
import com.nniett.kikaishin.web.persistence.entity.ShelfEntity;
import com.nniett.kikaishin.web.service.pojo.Book;
import com.nniett.kikaishin.web.service.pojo.Shelf;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ShelfMapper.class})
public interface BookMapper extends EntityPojoMapper<BookEntity, Book>{

    @Mappings({
            @Mapping(source = "id", target = "bookId"),
            @Mapping(source = "shelfId", target = "shelfId"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "updateDate", target = "updateDate"),
            @Mapping(source = "creationDate", target = "creationDate")
    })
    @Mapping(source = "shelf", target = "parentShelf", ignore = true)
    Book toPojo(BookEntity entity);
    List<Book> toPojos(List<BookEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source = "parentShelf", target = "shelf", ignore = true)
    BookEntity toEntity(Book pojo);
    List<BookEntity> toEntities(List<Book> pojos);
}

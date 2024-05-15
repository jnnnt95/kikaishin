package com.nniett.kikaishin.app.service.mapper.dto.book;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.shelf.ShelfCreationMapper;
import com.nniett.kikaishin.app.service.mapper.dto.topic.TopicCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Book;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookCreationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {ShelfCreationMapper.class, TopicCreationMapper.class})
public interface BookCreationMapper extends DtoPojoMapper<BookCreationDto, Book> {
    @Override
    @Mappings({
            @Mapping(source = "shelfId", target = "shelfId"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "topics", target = "topics"),

            @Mapping(target = "bookId", ignore = true),
            @Mapping(target = "active", ignore = true),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "parentShelf", ignore = true)
    })
    Book toPojo(BookCreationDto dto);

    @Override
    @InheritInverseConfiguration
    @Mapping(source = "topics", target = "topics", ignore = true)
    BookCreationDto toDto(Book pojo);
}

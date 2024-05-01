package com.nniett.kikaishin.web.service.mapper.dto.book;


import com.nniett.kikaishin.web.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.web.service.pojo.Book;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface BookUpdateMapper extends DtoPojoMapper<BookUpdateDto, Book> {
    @Mappings({
            @Mapping(source = "name", target = "name",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(source = "description", target = "description",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(source = "active", target = "active"),

            @Mapping(source = "bookId", target = "bookId"),
            @Mapping(target = "shelfId", ignore = true),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "topics", ignore = true),
            @Mapping(target = "parentShelf", ignore = true)
    })
    public Book toPojo(BookUpdateDto dto);
}
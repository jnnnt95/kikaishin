package com.nniett.kikaishin.app.service.mapper.dto.book;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.dto.BookDto;
import com.nniett.kikaishin.app.service.dto.write.book.BookUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface BookUpdateMapper extends DtoPojoMapper<BookUpdateDto, BookDto> {
    @Mappings({
            @Mapping(source = "bookId", target = "bookId"),
            @Mapping(source = "name", target = "name",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(source = "description", target = "description",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(source = "active", target = "active"),

            @Mapping(target = "shelfId", ignore = true),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "topics", ignore = true),
            @Mapping(target = "parentShelf", ignore = true),
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    BookDto toPojo(BookUpdateDto dto);
}
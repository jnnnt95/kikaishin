package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.virtual.BookInfoVirtualEntity;
import com.nniett.kikaishin.app.service.dto.BookInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookInfoMapper {
    @Mappings({
            @Mapping(source = "bookId", target = "bookId"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "age", target = "age"),
            @Mapping(source = "gradeAverage", target = "gradeAverage"),
            @Mapping(source = "totalTopics", target = "totalTopics"),
            @Mapping(source = "totalQuestions", target = "totalQuestions"),
            @Mapping(source = "totalQuestionsToReview", target = "totalQuestionsToReview"),
            @Mapping(source = "createDate", target = "createDate"),
            @Mapping(source = "topicIds", target = "topicIds")
    })
    BookInfoDto toBookInfo(BookInfoVirtualEntity entity);
    List<BookInfoDto> toBooksInfo(List<BookInfoVirtualEntity> entities);
}

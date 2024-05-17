package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.virtual.ShelfInfoVirtualEntity;
import com.nniett.kikaishin.app.service.dto.ShelfInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShelfInfoMapper {
    @Mappings({
            @Mapping(source = "shelfId", target = "shelfId"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "age", target = "age"),
            @Mapping(source = "gradeAverage", target = "gradeAverage"),
            @Mapping(source = "totalBooks", target = "totalBooks"),
            @Mapping(source = "totalTopics", target = "totalTopics"),
            @Mapping(source = "totalQuestions", target = "totalQuestions"),
            @Mapping(source = "totalQuestionsToReview", target = "totalQuestionsToReview"),
            @Mapping(source = "createDate", target = "createDate"),
            @Mapping(source = "bookIds", target = "bookIds")
    })
    ShelfInfoDto toShelfInfo(ShelfInfoVirtualEntity entity);
    List<ShelfInfoDto> toShelvesInfo(List<ShelfInfoVirtualEntity> entities);
}

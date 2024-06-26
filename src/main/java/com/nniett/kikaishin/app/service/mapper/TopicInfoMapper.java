package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.virtual.TopicInfoVirtualEntity;
import com.nniett.kikaishin.app.service.dto.TopicInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicInfoMapper {
    @Mappings({
            @Mapping(source = "topicId", target = "topicId"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "age", target = "age"),
            @Mapping(source = "gradeAverage", target = "gradeAverage"),
            @Mapping(source = "totalQuestions", target = "totalQuestions"),
            @Mapping(source = "totalQuestionsToReview", target = "totalQuestionsToReview"),
            @Mapping(source = "createDate", target = "createDate"),
            @Mapping(source = "questionIds", target = "questionIds")
    })
    TopicInfoDto toTopicInfo(TopicInfoVirtualEntity entity);
    List<TopicInfoDto> toTopicsInfo(List<TopicInfoVirtualEntity> entities);
}

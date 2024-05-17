package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.virtual.UserInfoVirtualEntity;
import com.nniett.kikaishin.app.service.dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "displayName", target = "displayName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "gradeAverage", target = "gradeAverage"),
            @Mapping(source = "totalShelves", target = "totalShelves"),
            @Mapping(source = "totalBooks", target = "totalBooks"),
            @Mapping(source = "totalTopics", target = "totalTopics"),
            @Mapping(source = "totalQuestions", target = "totalQuestions"),
            @Mapping(source = "totalQuestionsToReview", target = "totalQuestionsToReview"),
            @Mapping(source = "createDate", target = "createDate"),
            @Mapping(source = "shelfIds", target = "shelfIds")
    })
    UserInfoDto toUserInfo(UserInfoVirtualEntity entity);
}

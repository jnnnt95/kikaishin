package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.virtual.QuestionInfoVirtualEntity;
import com.nniett.kikaishin.app.service.dto.QuestionInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionInfoMapper {
    @Mappings({
            @Mapping(source = "questionId", target = "questionId"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "totalReviews", target = "totalReviews"),
            @Mapping(source = "gradeAverage", target = "gradeAverage"),
            @Mapping(source = "isToReview", target = "isToReview"),
            @Mapping(source = "body", target = "body"),
            @Mapping(source = "totalAnswers", target = "totalAnswers"),
            @Mapping(source = "totalClues", target = "totalClues"),
            @Mapping(source = "createDate", target = "createDate"),
            @Mapping(source = "lastReviewId", target = "lastReviewId"),
            @Mapping(source = "lastReviewDate", target = "lastReviewDate"),
            @Mapping(source = "nextReviewDate", target = "nextReviewDate"),
            @Mapping(source = "lastReviewGrade", target = "lastReviewGrade"),
            @Mapping(source = "age", target = "age")
    })
    QuestionInfoDto toQuestionInfo(QuestionInfoVirtualEntity entity);
    List<QuestionInfoDto> toQuestionsInfo(List<QuestionInfoVirtualEntity> entities);
}

package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.virtual.ReviewableQuestionVirtualEntity;
import com.nniett.kikaishin.app.service.dto.ReviewableQuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewableAnswerMapper.class, ReviewableClueMapper.class, ReviewModelMapper.class})
//Mapper not intended for entity but for populating review sets to provide to client.
public interface ReviewableQuestionMapper {
    @Mappings({
            @Mapping(source = "questionId", target = "questionId"),
            @Mapping(source = "body", target = "body"),
            @Mapping(source = "lookupKey", target = "lookupKey"),
            @Mapping(source = "answers", target = "answers"),
            @Mapping(source = "clues", target = "clues"),
            @Mapping(source = "creationDate", target = "creationDate"),
            @Mapping(source = "nextReviewDate", target = "nextReviewDate"),

            @Mapping(target = "orderIndex", ignore = true)
    })
    ReviewableQuestionDto toReviewableQuestion(ReviewableQuestionVirtualEntity qEntity);
    List<ReviewableQuestionDto> toReviewableQuestions(List<ReviewableQuestionVirtualEntity> qEntities);
}

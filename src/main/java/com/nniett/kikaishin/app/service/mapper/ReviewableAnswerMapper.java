package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.service.dto.ReviewableAnswerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewableAnswerMapper {
    @Mappings({
            @Mapping(source = "id", target = "answerId"),
            @Mapping(source = "orderIndex", target = "orderIndex"),
            @Mapping(source = "body", target = "body")
    })
    ReviewableAnswerDto toReviewableAnswer(AnswerEntity aEntity);
    List<ReviewableAnswerDto> toReviewableAnswers(List<AnswerEntity> aEntities);
}

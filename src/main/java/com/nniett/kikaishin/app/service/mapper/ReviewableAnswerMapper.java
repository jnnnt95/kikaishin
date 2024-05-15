package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.service.pojo.ReviewableAnswer;
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
    ReviewableAnswer toReviewableAnswer(AnswerEntity aEntity);
    List<ReviewableAnswer> toReviewableAnswers(List<AnswerEntity> aEntities);
}

package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.service.dto.ReviewableClueDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewableClueMapper {
    @Mappings({
            @Mapping(source = "id", target = "clueId"),
            @Mapping(source = "orderIndex", target = "orderIndex"),
            @Mapping(source = "body", target = "body")
    })
    ReviewableClueDto toReviewableClue(ClueEntity cEntity);
    List<ReviewableClueDto> toReviewableClues(List<ClueEntity> cEntities);
}

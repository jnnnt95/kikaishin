package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.ReviewModelEntity;
import com.nniett.kikaishin.app.service.dto.ReviewModelDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;


@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface ReviewModelMapper extends EntityPojoMapper<ReviewModelEntity, ReviewModelDto>{
    @Override
    @Mappings(value = {
            @Mapping(source = "id", target = "reviewModelId"),
            @Mapping(source = "question.id", target = "questionId"),
            @Mapping(source = "x0", target = "x0"),
            @Mapping(source = "x1", target = "x1"),
            @Mapping(source = "x2", target = "x2"),
            @Mapping(source = "expectedReviews", target = "expectedReviews"),
            @Mapping(target = "parentPK", ignore = true)
    })
    @Mapping(source = "question", target = "question", ignore = true)
    ReviewModelDto toPojo(ReviewModelEntity entity);
    @Override
    List<ReviewModelDto> toPojos(List<ReviewModelEntity> ReviewModel);

    @Override
    @InheritInverseConfiguration
    @Mapping(source = "question", target = "question", ignore = true)
    ReviewModelEntity toEntity(ReviewModelDto pojo);
    @Override
    List<ReviewModelEntity> toEntities(List<ReviewModelDto> pojos);
}

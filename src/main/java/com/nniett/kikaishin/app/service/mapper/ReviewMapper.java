package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import com.nniett.kikaishin.app.service.dto.ReviewDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, QuestionReviewGradeMapper.class})
public interface ReviewMapper extends EntityPojoMapper<ReviewEntity, ReviewDto> {
    @Override
    @Mappings({
            @Mapping(source = "id", target = "reviewId"),
            @Mapping(source = "questionReviewGrades", target = "questionGrades"),
            @Mapping(source = "userId", target = "username"),
            @Mapping(source = "creationDate", target = "createDate"),
            @Mapping(source = "questions", target = "questions"),


            @Mapping(target = "user", ignore = true),
            @Mapping(target = "PK", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    ReviewDto toPojo(ReviewEntity entity);
    @Override
    List<ReviewDto> toPojos(List<ReviewEntity> entities);

    @Override
    @InheritInverseConfiguration
    ReviewEntity toEntity(ReviewDto pojo);
    @Override
    List<ReviewEntity> toEntities(List<ReviewDto> pojos);
}

package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.service.dto.QuestionReviewGradeDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, ReviewMapper.class})
public interface QuestionReviewGradeMapper extends EntityPojoMapper<QuestionReviewGradeEntity, QuestionReviewGradeDto>{
    @Override
    @Mappings({
            @Mapping(source = "id", target = "questionReviewGradeId"),
            @Mapping(source = "questionId", target = "questionId"),
            @Mapping(source = "reviewId", target = "reviewId"),
            @Mapping(source = "gradeValue", target = "gradeValue"),

            @Mapping(target = "parentQuestion", ignore = true),
            @Mapping(target = "parentReview", ignore = true),
            @Mapping(target = "PK", ignore = true)
    })
    QuestionReviewGradeDto toPojo(QuestionReviewGradeEntity entity);
    @Override
    List<QuestionReviewGradeDto> toPojos(List<QuestionReviewGradeEntity> entities);

    @Override
    @InheritInverseConfiguration
    QuestionReviewGradeEntity toEntity(QuestionReviewGradeDto pojo);
    @Override
    List<QuestionReviewGradeEntity> toEntities(List<QuestionReviewGradeDto> pojos);
}

package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.service.pojo.QuestionReviewGrade;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, ReviewMapper.class})
public interface QuestionReviewGradeMapper extends EntityPojoMapper<QuestionReviewGradeEntity, QuestionReviewGrade>{
    @Override
    @Mappings({
            @Mapping(source = "id", target = "questionReviewGradeId"),
            @Mapping(source = "questionId", target = "questionId"),
            @Mapping(source = "reviewId", target = "reviewId"),
            @Mapping(source = "gradeValue", target = "gradeValue"),

            @Mapping(target = "parentQuestion", ignore = true),
            @Mapping(target = "parentReview", ignore = true)
    })
    QuestionReviewGrade toPojo(QuestionReviewGradeEntity entity);
    @Override
    List<QuestionReviewGrade> toPojos(List<QuestionReviewGradeEntity> entities);

    @Override
    @InheritInverseConfiguration
    QuestionReviewGradeEntity toEntity(QuestionReviewGrade pojo);
    @Override
    List<QuestionReviewGradeEntity> toEntities(List<QuestionReviewGrade> pojos);
}

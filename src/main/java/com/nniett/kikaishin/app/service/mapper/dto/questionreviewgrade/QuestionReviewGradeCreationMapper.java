package com.nniett.kikaishin.app.service.mapper.dto.questionreviewgrade;

import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.question.QuestionCreationMapper;
import com.nniett.kikaishin.app.service.mapper.dto.review.ReviewCreationMapper;
import com.nniett.kikaishin.app.service.dto.QuestionReviewGradeDto;
import com.nniett.kikaishin.app.service.dto.write.questionreviewgrade.QuestionReviewGradeCreationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {QuestionCreationMapper.class, ReviewCreationMapper.class})
public interface QuestionReviewGradeCreationMapper extends DtoPojoMapper<QuestionReviewGradeCreationDto, QuestionReviewGradeDto> {
    @Override
    @Mappings({
            @Mapping(source = "questionId", target = "questionId"),
            @Mapping(source = "reviewId", target = "reviewId"),
            @Mapping(source = "gradeValue", target = "gradeValue"),

            @Mapping(target = "questionReviewGradeId", ignore = true),
            @Mapping(target = "parentQuestion", ignore = true),
            @Mapping(target = "parentReview", ignore = true)
    })
    QuestionReviewGradeDto toPojo(QuestionReviewGradeCreationDto dto);

    @Override
    @InheritInverseConfiguration
    QuestionReviewGradeCreationDto toDto(QuestionReviewGradeDto pojo);
}

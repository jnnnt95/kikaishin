package com.nniett.kikaishin.app.service.mapper.dto.reviewmodel;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.question.QuestionCreationMapper;
import com.nniett.kikaishin.app.service.dto.ReviewModelDto;
import com.nniett.kikaishin.app.service.dto.write.reviewmodel.ReviewModelCreationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {QuestionCreationMapper.class})
public interface ReviewModelCreationMapper extends DtoPojoMapper<ReviewModelCreationDto, ReviewModelDto> {
    @Override
    @Mappings({
            @Mapping(source = "questionId", target = "questionId"),
            @Mapping(source = "x0", target = "x0"),
            @Mapping(source = "x1", target = "x1"),
            @Mapping(source = "x2", target = "x2"),
            @Mapping(source = "expectedReviews", target = "expectedReviews"),

            @Mapping(target = "reviewModelId", ignore = true),
            @Mapping(target = "question", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    ReviewModelDto toPojo(ReviewModelCreationDto dto);

    @Override
    @InheritInverseConfiguration
    ReviewModelCreationDto toDto(ReviewModelDto pojo);
}

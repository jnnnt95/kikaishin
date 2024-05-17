package com.nniett.kikaishin.app.service.mapper.dto.question;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.dto.QuestionDto;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionUpdateDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface QuestionUpdateMapper extends DtoPojoMapper<QuestionUpdateDto, QuestionDto> {
    @Override
    @Mappings({
            @Mapping(source = "questionId", target = "questionId"),
            @Mapping(source = "body", target = "body"),
            @Mapping(source = "active", target = "active"),

            @Mapping(target = "topicId", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "answers", ignore = true),
            @Mapping(target = "clues", ignore = true),
            @Mapping(target = "reviewGrades", ignore = true),
            @Mapping(target = "topic", ignore = true),
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "parentPK", ignore = true),
            @Mapping(target = "reviewModel", ignore = true)
    })
    QuestionDto toPojo(QuestionUpdateDto dto);

    @Override
    @InheritInverseConfiguration
    QuestionUpdateDto toDto(QuestionDto pojo);
}

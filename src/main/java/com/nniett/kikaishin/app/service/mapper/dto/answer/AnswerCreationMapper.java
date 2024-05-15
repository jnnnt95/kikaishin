package com.nniett.kikaishin.app.service.mapper.dto.answer;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.question.QuestionCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Answer;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerCreationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {QuestionCreationMapper.class})
public interface AnswerCreationMapper extends DtoPojoMapper<AnswerCreationDto, Answer> {
    @Override
    @Mappings({
            @Mapping(source = "questionId", target = "questionId"),
            @Mapping(source = "orderIndex", target = "orderIndex"),
            @Mapping(source = "body", target = "body"),

            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "answerId", ignore = true),
            @Mapping(target = "question", ignore = true)
    })
    Answer toPojo(AnswerCreationDto dto);

    @Override
    @InheritInverseConfiguration
    AnswerCreationDto toDto(Answer pojo);
}

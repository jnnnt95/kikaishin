package com.nniett.kikaishin.app.service.mapper.dto.answer;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.dto.AnswerDto;
import com.nniett.kikaishin.app.service.dto.write.answer.AnswerUpdateDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AnswerUpdateMapper extends DtoPojoMapper<AnswerUpdateDto, AnswerDto> {
    @Override
    @Mappings({
            @Mapping(source = "answerId", target = "answerId"),
            @Mapping(source = "orderIndex", target = "orderIndex"),
            @Mapping(source = "body", target = "body"),

            @Mapping(target = "questionId", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "question", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    AnswerDto toPojo(AnswerUpdateDto dto);

    @Override
    @InheritInverseConfiguration
    AnswerUpdateDto toDto(AnswerDto pojo);
}

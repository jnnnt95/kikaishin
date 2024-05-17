package com.nniett.kikaishin.app.service.mapper.dto.clue;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.question.QuestionCreationMapper;
import com.nniett.kikaishin.app.service.dto.ClueDto;
import com.nniett.kikaishin.app.service.dto.write.clue.ClueCreationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {QuestionCreationMapper.class})
public interface ClueCreationMapper extends DtoPojoMapper<ClueCreationDto, ClueDto> {
    @Override
    @Mappings({
            @Mapping(source = "questionId", target = "questionId"),
            @Mapping(source = "orderIndex", target = "orderIndex"),
            @Mapping(source = "body", target = "body"),

            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "clueId", ignore = true),
            @Mapping(target = "question", ignore = true)
    })
    ClueDto toPojo(ClueCreationDto dto);

    @Override
    @InheritInverseConfiguration
    ClueCreationDto toDto(ClueDto pojo);
}

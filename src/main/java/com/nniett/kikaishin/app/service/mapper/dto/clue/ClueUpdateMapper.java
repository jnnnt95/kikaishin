package com.nniett.kikaishin.app.service.mapper.dto.clue;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.pojo.Clue;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueUpdateDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ClueUpdateMapper extends DtoPojoMapper<ClueUpdateDto, Clue> {
    @Override
    @Mappings({
            @Mapping(source = "clueId", target = "clueId"),
            @Mapping(source = "orderIndex", target = "orderIndex"),
            @Mapping(source = "body", target = "body"),

            @Mapping(target = "questionId", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "question", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    Clue toPojo(ClueUpdateDto dto);

    @Override
    @InheritInverseConfiguration
    ClueUpdateDto toDto(Clue pojo);
}

package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.service.dto.ClueDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;


@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface ClueMapper extends EntityPojoMapper<ClueEntity, ClueDto> {
    @Override
    @Mappings({
            @Mapping(source = "id", target = "clueId"),
            @Mapping(source = "question.id", target = "questionId"),
            @Mapping(source = "orderIndex", target = "orderIndex"),
            @Mapping(source = "body", target = "body"),

            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "PK", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    @Mapping(target = "question", ignore = true)
    ClueDto toPojo(ClueEntity entity);
    @Override
    List<ClueDto> toPojos(List<ClueEntity> entities);

    @Override
    @InheritInverseConfiguration
    @Mapping(source = "question", target = "question", ignore = true)
    ClueEntity toEntity(ClueDto pojo);
    @Override
    List<ClueEntity> toEntities(List<ClueDto> pojos);
}

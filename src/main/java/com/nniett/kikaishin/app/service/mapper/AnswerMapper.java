package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.service.dto.AnswerDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface AnswerMapper extends EntityPojoMapper<AnswerEntity, AnswerDto>{
    @Override
    @Mappings({
            @Mapping(source = "id", target = "answerId"),
            @Mapping(source = "question.id", target = "questionId"),
            @Mapping(source = "orderIndex", target = "orderIndex"),
            @Mapping(source = "body", target = "body"),

            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "PK", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    @Mapping(target = "question", ignore = true)
    AnswerDto toPojo(AnswerEntity entity);
    @Override
    List<AnswerDto> toPojos(List<AnswerEntity> entities);

    @Override
    @InheritInverseConfiguration
    @Mapping(source = "question", target = "question", ignore = true)
    AnswerEntity toEntity(AnswerDto pojo);
    @Override
    List<AnswerEntity> toEntities(List<AnswerDto> pojos);
}

package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.service.pojo.Answer;
import com.nniett.kikaishin.app.service.pojo.Question;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface AnswerMapper extends EntityPojoMapper<AnswerEntity, Answer>{
    @Override
    @Mappings({
            @Mapping(source = "id", target = "answerId"),
            @Mapping(source = "question.id", target = "questionId"),
            @Mapping(source = "orderIndex", target = "orderIndex"),
            @Mapping(source = "body", target = "body"),

            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
    })
    @Mapping(target = "question", ignore = true)
    Answer toPojo(AnswerEntity entity);
    @Override
    List<Answer> toPojos(List<AnswerEntity> entities);

    @Override
    @InheritInverseConfiguration
    @Mapping(source = "question", target = "question", ignore = true)
    AnswerEntity toEntity(Answer pojo);
    @Override
    List<AnswerEntity> toEntities(List<Answer> pojos);
}

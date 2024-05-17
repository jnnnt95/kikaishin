package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.service.dto.QuestionDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        TopicMapper.class,
        AnswerMapper.class,
        ClueMapper.class,
        ReviewModelMapper.class,
        QuestionReviewGradeMapper.class
})
public interface QuestionMapper extends EntityPojoMapper<QuestionEntity, QuestionDto>{
    @Override
    @Mappings({
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "body", target = "body"),
            @Mapping(source = "creationDate", target = "createDate"),
            @Mapping(source = "updateDate", target = "updateDate"),
            @Mapping(source = "id", target = "questionId"),
            @Mapping(source = "reviewModel", target = "reviewModel"),
            @Mapping(source = "answers", target = "answers"),
            @Mapping(source = "clues", target = "clues"),
            @Mapping(source = "questionReviewGrades", target = "reviewGrades"),

            @Mapping(target = "topicId", ignore = true),
            @Mapping(target = "PK", ignore = true),
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    @Mapping(source = "topic", target = "topic", ignore = true)
    QuestionDto toPojo(QuestionEntity entity);
    @Override
    List<QuestionDto> toPojos(List<QuestionEntity> entities);

    @Override
    @InheritInverseConfiguration
    @Mapping(source = "topic", target = "topic", ignore = true)
    QuestionEntity toEntity(QuestionDto pojo);
    @Override
    List<QuestionEntity> toEntities(List<QuestionDto> pojos);
}

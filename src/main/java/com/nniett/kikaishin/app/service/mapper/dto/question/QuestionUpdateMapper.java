package com.nniett.kikaishin.app.service.mapper.dto.question;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.pojo.Question;
import com.nniett.kikaishin.app.service.pojo.Topic;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicUpdateDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface QuestionUpdateMapper extends DtoPojoMapper<QuestionUpdateDto, Question> {
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
            @Mapping(target = "topic", ignore = true)
    })
    public Question toPojo(QuestionUpdateDto dto);

    @Override
    @InheritInverseConfiguration
    public QuestionUpdateDto toDto(Question pojo);
}

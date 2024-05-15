package com.nniett.kikaishin.app.service.mapper.dto.question;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.answer.AnswerCreationMapper;
import com.nniett.kikaishin.app.service.mapper.dto.clue.ClueCreationMapper;
import com.nniett.kikaishin.app.service.mapper.dto.reviewmodel.ReviewModelCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Question;
import com.nniett.kikaishin.app.service.pojo.ReviewModel;
import com.nniett.kikaishin.app.service.pojo.Topic;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicCreationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {
        TopicCreationDto.class,
        AnswerCreationMapper.class,
        ClueCreationMapper.class,
        ReviewModelCreationMapper.class}
)
public interface QuestionCreationMapper extends DtoPojoMapper<QuestionCreationDto, Question> {
    @Override
    @Mappings({
            @Mapping(source = "topicId", target = "topicId"),
            @Mapping(source = "body", target = "body"),
            @Mapping(source = "reviewModel", target = "reviewModel"),
            @Mapping(source = "answers", target = "answers"),
            @Mapping(source = "clues", target = "clues"),




            @Mapping(target = "questionId", ignore = true),
            @Mapping(target = "active", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "reviewGrades", ignore = true),
            @Mapping(target = "topic", ignore = true)
    })
    public Question toPojo(QuestionCreationDto dto);

    @Override
    @InheritInverseConfiguration
    public QuestionCreationDto toDto(Question pojo);
}

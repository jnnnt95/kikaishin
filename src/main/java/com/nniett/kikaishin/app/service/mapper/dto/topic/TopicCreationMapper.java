package com.nniett.kikaishin.app.service.mapper.dto.topic;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.question.QuestionCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Topic;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicCreationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {TopicCreationMapper.class, QuestionCreationMapper.class})
public interface TopicCreationMapper extends DtoPojoMapper<TopicCreationDto, Topic> {
    @Override
    @Mappings({
            @Mapping(source = "bookId", target = "bookId"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "questions", target = "questions"),

            @Mapping(target = "topicId", ignore = true),
            @Mapping(target = "active", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "lookupKey", ignore = true),
            @Mapping(target = "parentBook", ignore = true)
    })
    Topic toPojo(TopicCreationDto dto);

    @Override
    @InheritInverseConfiguration
    @Mapping(source = "questions", target = "questions", ignore = true)
    TopicCreationDto toDto(Topic pojo);
}

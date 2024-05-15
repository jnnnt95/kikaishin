package com.nniett.kikaishin.app.service.mapper.dto.topic;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.mapper.dto.question.QuestionCreationMapper;
import com.nniett.kikaishin.app.service.pojo.Topic;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicUpdateDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TopicUpdateMapper extends DtoPojoMapper<TopicUpdateDto, Topic> {
    @Override
    @Mappings({
            @Mapping(source = "topicId", target = "topicId"),
            @Mapping(source = "name", target = "name",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(source = "description", target = "description",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "active", ignore = true),

            @Mapping(target = "bookId", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "questions", ignore = true),
            @Mapping(target = "lookupKey", ignore = true),
            @Mapping(target = "parentBook", ignore = true)
    })
    public Topic toPojo(TopicUpdateDto dto);

    @Override
    @InheritInverseConfiguration
    public TopicUpdateDto toDto(Topic pojo);
}

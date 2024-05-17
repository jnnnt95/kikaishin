package com.nniett.kikaishin.app.service.mapper.dto.topic;


import com.nniett.kikaishin.app.service.mapper.dto.DtoPojoMapper;
import com.nniett.kikaishin.app.service.dto.TopicDto;
import com.nniett.kikaishin.app.service.dto.write.topic.TopicUpdateDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TopicUpdateMapper extends DtoPojoMapper<TopicUpdateDto, TopicDto> {
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
            @Mapping(target = "parentBook", ignore = true),
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    TopicDto toPojo(TopicUpdateDto dto);

    @Override
    @InheritInverseConfiguration
    TopicUpdateDto toDto(TopicDto pojo);
}

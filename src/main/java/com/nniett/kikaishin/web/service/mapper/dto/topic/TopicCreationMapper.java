package com.nniett.kikaishin.web.service.mapper.dto.topic;


import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {TopicCreationMapper.class})
public interface TopicCreationMapper {
}

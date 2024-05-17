package com.nniett.kikaishin.app.service.mapper;

import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.service.dto.TopicDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookMapper.class, QuestionMapper.class})
public interface TopicMapper extends EntityPojoMapper<TopicEntity, TopicDto>{

    @Mappings({
            @Mapping(source = "id", target = "topicId"),
            @Mapping(source = "lookupKey", target = "lookupKey"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "updateDate", target = "updateDate"),
            @Mapping(source = "creationDate", target = "createDate"),
            @Mapping(source = "questions", target = "questions"),
            @Mapping(target = "PK", ignore = true),
            @Mapping(target = "children", ignore = true),
            @Mapping(target = "parentPK", ignore = true)
    })
    @Mapping(source = "book", target = "parentBook", ignore = true)
    TopicDto toPojo(TopicEntity entity);
    @Override
    List<TopicDto> toPojos(List<TopicEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source = "parentBook", target = "book", ignore = true)
    TopicEntity toEntity(TopicDto pojo);
    @Override
    List<TopicEntity> toEntities(List<TopicDto> pojos);
}

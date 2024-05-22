package com.nniett.kikaishin.app.service.crud.topic;

import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.service.construction.UpdateService;
import com.nniett.kikaishin.app.service.dto.TopicDto;
import com.nniett.kikaishin.app.service.mapper.TopicMapper;
import com.nniett.kikaishin.app.service.mapper.dto.topic.TopicUpdateMapper;
import com.nniett.kikaishin.app.service.dto.write.topic.TopicUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class TopicUpdateService
        extends UpdateService
        <
                TopicEntity,
                Integer,
                TopicUpdateDto,
                TopicDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(TopicUpdateService.class);

    public TopicUpdateService(
            TopicRepository repository,
            TopicMapper entityPojoMapper,
            @Qualifier("topicUpdateMapperImpl")
            TopicUpdateMapper updateMapper
    ) {
        super(repository, entityPojoMapper, updateMapper);
        logger.info("TopicUpdateService initialized.");
    }

    @Override
    public void populateEntityForUpdate(TopicEntity entity, TopicDto pojo) {
        logger.debug("Populating topic's changed fields.");
        if(pojo.getName() != null &&
                !pojo.getName().isEmpty() &&
                !pojo.getName().equals(entity.getName())) {
            logger.trace("Populating topic's name with {}.", pojo.getName());
            entity.setName(pojo.getName());
        }
        if(pojo.getDescription() != null &&
                !pojo.getDescription().isEmpty() &&
                !pojo.getDescription().equals(entity.getDescription())) {
            logger.trace("Populating topic's description with {}.", pojo.getDescription());
            entity.setDescription(pojo.getDescription());
        }
        if(pojo.getActive() != null &&
                pojo.getActive() != entity.getActive()) {
            logger.debug("Populating topic's field active as {}.", pojo.getActive());
            entity.setActive(pojo.getActive());
        }
    }
}

package com.nniett.kikaishin.app.service.crud.topic;

import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.TopicMapper;
import com.nniett.kikaishin.app.service.dto.TopicDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TopicReadService
        extends ReadService
        <
                TopicEntity,
                Integer,
                TopicDto
                >
{
    private static final Logger logger = LoggerFactory.getLogger(TopicReadService.class);

    public TopicReadService(
            TopicRepository repository,
            TopicMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
        logger.info("TopicReadService initialized.");
    }
    
}

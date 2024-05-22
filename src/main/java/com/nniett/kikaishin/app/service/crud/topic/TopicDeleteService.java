package com.nniett.kikaishin.app.service.crud.topic;

import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.service.construction.DeleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TopicDeleteService
        extends DeleteService<TopicEntity, Integer>
{
    private static final Logger logger = LoggerFactory.getLogger(TopicDeleteService.class);

    public TopicDeleteService(TopicRepository repository) {
        super(repository);
        logger.info("TopicDeleteService initialized.");
    }

}

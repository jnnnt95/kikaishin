package com.nniett.kikaishin.app.service.crud.topic;

import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.dto.TopicDto;
import com.nniett.kikaishin.app.service.mapper.TopicMapper;
import com.nniett.kikaishin.app.service.mapper.dto.topic.TopicCreationMapper;
import com.nniett.kikaishin.app.service.dto.write.topic.TopicCreationDto;
import com.nniett.kikaishin.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Repository
public class TopicCreateService
        extends CreateService
        <
                        TopicEntity,
                        Integer,
                TopicDto,
                        TopicCreationDto
                        >
{
    private static final Logger logger = LoggerFactory.getLogger(TopicCreateService.class);
    private static final Random RANDOM = new Random();
    private static final char[] LOOKUP_KEY_CONSTRUCTION_CHARS = new char[]
            {
                    'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n',
                    'o', 'p', 'q', 'r', 's', 't', 'u',
                    'v', 'w', 'x', 'y', 'z', 'A', 'B',
                    'C', 'D', 'E', 'F', 'G', 'H', 'I',
                    'J', 'K', 'L', 'M', 'N', 'O', 'P',
                    'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                    'X', 'Y', 'Z', '0', '1', '2', '3',
                    '4', '5', '6', '7', '8', '9', '@',
                    '#', '$', '%', '&', '=', '_', '?',
            };

    public TopicCreateService(
            TopicRepository repository,
            TopicMapper entityPojoMapper,
            @Qualifier("topicCreationMapperImpl")
            TopicCreationMapper createMapper
    ) {
        super(repository, entityPojoMapper, createMapper);
        logger.info("TopicCreateService initialized.");
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void populateAsDefaultForCreation(TopicEntity pojo) {
        logger.debug("Populating default fields for new topic.");
        logger.trace("Populating default field active as true.");
        pojo.setActive(true);
        String lookupKey = generateNewLookupKey();
        logger.trace("Populating lookup field as {}.", lookupKey);
        pojo.setLookupKey(lookupKey);
    }

    public String generateNewLookupKey() {
        logger.debug("Generating lookup key.");
        StringBuilder lookupBuilder;
        do {
            lookupBuilder = new StringBuilder();
            for(int i = 0; i < Constants.LOOKUP_KEY_SIZE; i++) {
                lookupBuilder.append(generateRandomChar());
            }
        } while (!((TopicRepository) getRepository()).findByLookupKey(lookupBuilder.toString()).isEmpty());
        return lookupBuilder.toString();
    }

    private static char generateRandomChar() {
        return LOOKUP_KEY_CONSTRUCTION_CHARS[RANDOM.nextInt(LOOKUP_KEY_CONSTRUCTION_CHARS.length)];
    }
}

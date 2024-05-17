package com.nniett.kikaishin.app.service.crud.topic;

import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.service.construction.CreateService;
import com.nniett.kikaishin.app.service.dto.TopicDto;
import com.nniett.kikaishin.app.service.mapper.TopicMapper;
import com.nniett.kikaishin.app.service.mapper.dto.topic.TopicCreationMapper;
import com.nniett.kikaishin.app.service.dto.write.topic.TopicCreationDto;
import com.nniett.kikaishin.common.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
    }

//    public String generateNewLookupKey(int id) {
//        StringBuilder lookupBuilderInverse = new StringBuilder();
//        StringBuilder lookupBuilder = new StringBuilder();
//        String s_id = String.valueOf(id);
//
//        for(int i = s_id.length() - 1; i >= 0; i--) {
//            int val = Integer.parseInt(String.valueOf(s_id.charAt(i)));
//            lookupBuilderInverse.append(
//                    LOOKUP_KEY_CONSTRUCTION_CHARS[val]
//            );
//            lookupBuilderInverse.append(
//                    LOOKUP_KEY_CONSTRUCTION_CHARS[val+1]
//            );
//            lookupBuilderInverse.append(
//                    LOOKUP_KEY_CONSTRUCTION_CHARS[val+2]
//            );
//        }
//
//        for(int i = 0; i < Constants.LOOKUP_KEY_SIZE - lookupBuilderInverse.length(); i++) {
//            lookupBuilderInverse.append(LOOKUP_KEY_CONSTRUCTION_CHARS[0]);
//        }
//
//        for(int i = lookupBuilderInverse.length() - 1; i >= 0; i--) {
//            lookupBuilder.append(lookupBuilderInverse.charAt(i));
//        }
//
//        return lookupBuilder.toString();
//    }

    @Override
    @Transactional(Transactional.TxType.MANDATORY)
    public void populateAsDefaultForCreation(TopicEntity pojo) {
        pojo.setActive(true);
        pojo.setLookupKey(generateNewLookupKey());
    }

    public String generateNewLookupKey() {
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

package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.persistence.repository.virtual.TopicInfoVirtualRepository;
import com.nniett.kikaishin.app.service.construction.ActivateableService;
import com.nniett.kikaishin.app.service.dto.TopicDto;
import com.nniett.kikaishin.app.service.dto.TopicInfoDto;
import com.nniett.kikaishin.app.service.mapper.TopicInfoMapper;
import com.nniett.kikaishin.app.service.dto.write.topic.TopicCreationDto;
import com.nniett.kikaishin.app.service.dto.write.topic.TopicUpdateDto;
import com.nniett.kikaishin.app.service.crud.topic.TopicCreateService;
import com.nniett.kikaishin.app.service.crud.topic.TopicReadService;
import com.nniett.kikaishin.app.service.crud.topic.TopicUpdateService;
import com.nniett.kikaishin.app.service.crud.topic.TopicDeleteService;
import com.nniett.kikaishin.app.web.controller.construction.UsesHttpServletRequest;
import com.nniett.kikaishin.app.web.security.CanRetrieveUsernameFromJWT;
import com.nniett.kikaishin.app.web.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class TopicService
        extends ActivateableService
        <
                TopicEntity,
                Integer,
                TopicDto,
                TopicCreationDto,
                TopicUpdateDto
                >
        implements UsesHttpServletRequest, CanRetrieveUsernameFromJWT
{
    private static final Logger logger = LoggerFactory.getLogger(TopicService.class);

    private final TopicInfoVirtualRepository topicInfoRepository;
    private final TopicInfoMapper topicInfoMapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public TopicService(
            TopicRepository repository,
            TopicCreateService createService,
            TopicReadService readService,
            TopicUpdateService updateService,
            TopicDeleteService deleteService,
            TopicInfoVirtualRepository topicInfoRepository,
            TopicInfoMapper topicInfoMapper,
            JwtUtils jwtUtils
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.topicInfoRepository = topicInfoRepository;
        this.topicInfoMapper = topicInfoMapper;
        this.jwtUtils = jwtUtils;
        logger.info("TopicService initialized.");
    }


    @Override
    public void populateAsDefaultForCreation(TopicEntity entity) {
        getCreateService().populateAsDefaultForCreation(entity);
    }

    @Override
    public void populateEntityForUpdate(TopicEntity entity, TopicDto pojo) {
        getUpdateService().populateEntityForUpdate(entity, pojo);
    }

    @Override
    public TopicEntity findEntityByDto(TopicUpdateDto updateDto) {
        int id = updateDto.getTopicId();
        return getRepository().findById(id).orElseThrow();
    }

    @Override
    public ListCrudRepository<TopicEntity, Integer> getActivateableRepository() {
        return getRepository();
    }

    public TopicInfoDto getTopicInfo(Integer topicId) {
        logger.debug("Retrieving topic info.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Topic info being gathered for username {}.", username);
        return topicInfoMapper.
                toTopicInfo(
                        topicInfoRepository.
                                getTopicsInfoById(username, Collections.singletonList(topicId)).get(0)
                );
    }

    public List<TopicInfoDto> getTopicsInfo(List<Integer> topicIds) {
        logger.debug("Retrieving topics info.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Topics info being gathered for username {}.", username);
        return topicInfoMapper.toTopicsInfo(topicInfoRepository.getTopicsInfoById(username, topicIds));
    }

    public Integer countExistingIds(List<Integer> topicIds) {
        logger.debug("Retrieving count of topics by ids.");
        String username = getUsernameFromJWT(getHttpServletRequest(), this.jwtUtils);
        logger.trace("Counting topics by ids for username {}.", username);
        return ((TopicRepository) getRepository()).countByIdIn(username, topicIds);
    }
}
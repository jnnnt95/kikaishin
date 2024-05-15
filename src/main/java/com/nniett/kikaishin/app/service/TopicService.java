package com.nniett.kikaishin.app.service;

import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.persistence.repository.virtual.TopicInfoVirtualRepository;
import com.nniett.kikaishin.app.service.construction.ActivateableService;
import com.nniett.kikaishin.app.service.mapper.TopicInfoMapper;
import com.nniett.kikaishin.app.service.pojo.Topic;
import com.nniett.kikaishin.app.service.pojo.TopicInfo;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicUpdateDto;
import com.nniett.kikaishin.app.service.crud.topic.TopicCreateService;
import com.nniett.kikaishin.app.service.crud.topic.TopicReadService;
import com.nniett.kikaishin.app.service.crud.topic.TopicUpdateService;
import com.nniett.kikaishin.app.service.crud.topic.TopicDeleteService;
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
                Topic,
                TopicCreationDto,
                TopicUpdateDto
                >
{

    private final TopicInfoVirtualRepository topicInfoRepository;
    private final TopicInfoVirtualRepository t;
    private final TopicInfoMapper topicInfoMapper;

    @Autowired
    public TopicService(
            TopicRepository repository,
            TopicCreateService createService,
            TopicReadService readService,
            TopicUpdateService updateService,
            TopicDeleteService deleteService,
            TopicInfoVirtualRepository topicInfoRepository,
            TopicInfoMapper topicInfoMapper,
            TopicInfoVirtualRepository t
    ) {
        super(repository, createService, readService, updateService, deleteService);
        this.topicInfoRepository = topicInfoRepository;
        this.topicInfoMapper = topicInfoMapper;
        this.t = t;
    }


    @Override
    public void populateAsDefaultForCreation(TopicEntity entity) {
        getCreateService().populateAsDefaultForCreation(entity);
    }

    @Override
    public void populateEntityForUpdate(TopicEntity entity, Topic pojo) {
        getUpdateService().populateEntityForUpdate(entity, pojo);
    }

    @Override
    public TopicEntity findEntityByDto(TopicUpdateDto updateDto) {
        int id = updateDto.getTopicId();
        return getRepository().findById(id).get();
    }

    @Override
    public ListCrudRepository<TopicEntity, Integer> getActivateableRepository() {
        return getRepository();
    }



    public TopicInfo getTopicInfo(Integer topicId) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return topicInfoMapper.
                toTopicInfo(
                        topicInfoRepository.
                                getTopicsInfoById(username, Collections.singletonList(topicId)).get(0)
                );
    }

    public List<TopicInfo> getTopicsInfo(List<Integer> topicIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return topicInfoMapper.toTopicsInfo(topicInfoRepository.getTopicsInfoById(username, topicIds));
    }

    public Integer countExistingIds(List<Integer> topicIds) {
        //TODO: username should be retrieved appropriately from JWT.
        String username = "1";
        return ((TopicRepository) getRepository()).countByIdIn(username, topicIds);
    }
}
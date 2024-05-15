package com.nniett.kikaishin.app.service.crud.topic;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.persistence.repository.TopicRepository;
import com.nniett.kikaishin.app.service.construction.ReadService;
import com.nniett.kikaishin.app.service.mapper.BookMapper;
import com.nniett.kikaishin.app.service.mapper.TopicMapper;
import com.nniett.kikaishin.app.service.pojo.Book;
import com.nniett.kikaishin.app.service.pojo.Topic;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookUpdateDto;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicUpdateDto;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TopicReadService
        extends ReadService
        <
                TopicEntity,
                Integer,
                Topic
                >
{

    public TopicReadService(
            TopicRepository repository,
            TopicMapper entityPojoMapper
    ) {
        super(repository, entityPojoMapper);
    }
    
}

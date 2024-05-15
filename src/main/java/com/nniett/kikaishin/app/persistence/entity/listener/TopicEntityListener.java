package com.nniett.kikaishin.app.persistence.entity.listener;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import com.nniett.kikaishin.app.service.BeanUtil;
import com.nniett.kikaishin.app.service.crud.topic.TopicCreateService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class TopicEntityListener {

    @PostPersist
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void postPersist(TopicEntity topicEntity) {
        TopicCreateService service = (TopicCreateService) BeanUtil.getBean("topicCreateService");
        topicEntity.setLookupKey(service.generateNewLookupKey());

        if(topicEntity.getQuestions() != null) {
            for(QuestionEntity child: topicEntity.getQuestions()) {
                child.setTopic(topicEntity);
                child.setTopicId(topicEntity.getId());
            }
        }
    }

}

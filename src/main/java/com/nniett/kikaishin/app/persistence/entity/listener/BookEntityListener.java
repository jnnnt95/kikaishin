package com.nniett.kikaishin.app.persistence.entity.listener;

import com.nniett.kikaishin.app.persistence.entity.BookEntity;
import com.nniett.kikaishin.app.persistence.entity.TopicEntity;
import jakarta.persistence.PostPersist;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BookEntityListener {

    @PostPersist
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void childFKPropagate(BookEntity entity) {
        if(entity.getTopics() != null) {
            for(TopicEntity child: entity.getTopics()) {
                child.setBook(entity);
                child.setBookId(entity.getId());
            }
        }
    }
    
}

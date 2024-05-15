package com.nniett.kikaishin.app.persistence.entity.listener;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewModelEntity;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class QuestionEntityListener {

    @PostPersist
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void postPersist(QuestionEntity questionEntity) {
        ReviewModelEntity reviewModelEntity = questionEntity.getReviewModel();
        //reviewModelEntity.setQuestionId(questionEntity.getId());
        reviewModelEntity.setQuestion(questionEntity);
    }
}

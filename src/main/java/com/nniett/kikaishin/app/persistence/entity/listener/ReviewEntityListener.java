package com.nniett.kikaishin.app.persistence.entity.listener;

import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewEntity;
import jakarta.persistence.PostPersist;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ReviewEntityListener {

    @PostPersist
    @Transactional(propagation = Propagation.REQUIRED)
    public void postPersist(ReviewEntity entity) {
        entity.setId(entity.getId());
        if(entity.getQuestionReviewGrades() != null) {
            for(QuestionReviewGradeEntity child: entity.getQuestionReviewGrades()) {
                child.setReviewId(entity.getId());
            }
        }
    }

}

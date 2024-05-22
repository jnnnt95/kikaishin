package com.nniett.kikaishin.app.persistence.entity.virtual;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class TopicInfoVirtualEntity {

    private static final Logger logger = LoggerFactory.getLogger(TopicInfoVirtualEntity.class);

    private Integer topicId;
    private Boolean active;
    private Long age; // calculated
    private Float gradeAverage; // calculated
    private Integer totalQuestions;// calculated
    private Long totalQuestionsToReview; // calculated
    private LocalDateTime createDate;
    private List<QuestionInfoVirtualEntity> questionsInfo;
    private List<Integer> questionIds;

    public void calculate() {
        logger.debug("Executing TopicInfoVirtualEntity calculate action.");
        logger.debug("Calculate action being executed for TopicInfoVirtualEntity representation of topic id {}.", topicId);
        updateAge();
        updateGradeAverage();
        updateTotalQuestions();
        updateTotalQuestionsToReview();
        updateQuestionIds();
    }

    private void updateQuestionIds() {
        logger.debug("Updating question ids.");
        this.questionIds = new ArrayList<>();
        questionsInfo.forEach(q -> questionIds.add(q.getQuestionId()));
    }

    private void updateAge() {
        logger.debug("Updating age.");
        this.age = ChronoUnit.DAYS.between(createDate, LocalDateTime.now());
    }

    private void updateGradeAverage() {
        logger.debug("Updating grade average.");
        this.gradeAverage =
                (float) this.questionsInfo.stream().
                        mapToDouble(q -> {
                            if(q.getGradeAverage() == null) return 0;
                            else return q.getGradeAverage();
                        }).average().orElse(0);
    }

    private void updateTotalQuestions() {
        logger.debug("Updating total questions.");
        this.totalQuestions = this.questionsInfo.size();
    }

    private void updateTotalQuestionsToReview() {
        logger.debug("Updating total questions to review.");
        this.totalQuestionsToReview =
                this.questionsInfo.stream().filter(QuestionInfoVirtualEntity::getIsToReview).count();
    }


}

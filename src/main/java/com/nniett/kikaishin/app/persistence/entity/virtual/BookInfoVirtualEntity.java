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
public class BookInfoVirtualEntity {

    private static final Logger logger = LoggerFactory.getLogger(BookInfoVirtualEntity.class);

    private Integer bookId;
    private Boolean active;
    private Long age; // calculated
    private Float gradeAverage; // calculated
    private Integer totalTopics; // calculated
    private Long totalQuestions;// calculated
    private Long totalQuestionsToReview; // calculated
    private LocalDateTime createDate;
    private List<TopicInfoVirtualEntity> topicsInfo;
    private List<Integer> topicIds;

    public void calculate() {
        logger.debug("Executing BookInfoVirtualEntity calculate action.");
        logger.debug("Calculate action being executed for BookInfoVirtualEntity representation of book id {}.", bookId);
        updateAge();
        updateGradeAverage();
        updateTotalTopics();
        updateTotalQuestions();
        updateTotalQuestionsToReview();
        updateTopicIds();
    }

    private void updateTotalTopics() {
        logger.debug("Updating total topics.");
        this.totalTopics = this.topicsInfo.size();
    }

    private void updateTopicIds() {
        logger.debug("Updating topic ids.");
        this.topicIds = new ArrayList<>();
        topicsInfo.forEach(q -> topicIds.add(q.getTopicId()));
    }

    private void updateAge() {
        logger.debug("Updating age.");
        this.age = ChronoUnit.DAYS.between(createDate, LocalDateTime.now());
    }

    private void updateGradeAverage() {
        logger.debug("Updating grade average.");
        this.gradeAverage =
                (float) this.topicsInfo.stream().
                        mapToDouble(q -> {
                            if(q.getGradeAverage() == null) return 0;
                            else return q.getGradeAverage();
                        }).average().orElse(0);
    }

    private void updateTotalQuestions() {
        logger.debug("Updating total questions.");
        this.totalQuestions = this.topicsInfo.stream().mapToLong(TopicInfoVirtualEntity::getTotalQuestions).sum();
    }

    private void updateTotalQuestionsToReview() {
        logger.debug("Updating total questions to review.");
        this.totalQuestionsToReview =
                this.topicsInfo.stream().flatMap(t -> t.getQuestionsInfo().stream()).
                        filter(QuestionInfoVirtualEntity::getIsToReview).count();
    }


}

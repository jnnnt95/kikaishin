package com.nniett.kikaishin.app.persistence.entity.virtual;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookInfoVirtualEntity {
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
        updateAge();
        updateGradeAverage();
        updateTotalTopics();
        updateTotalQuestions();
        updateTotalQuestionsToReview();
        updateTopicIds();
    }

    private void updateTotalTopics() {
        this.totalTopics = this.topicsInfo.size();
    }

    private void updateTopicIds() {
        this.topicIds = new ArrayList<>();
        topicsInfo.forEach(q -> topicIds.add(q.getTopicId()));
    }

    private void updateAge() {
        this.age = ChronoUnit.DAYS.between(createDate, LocalDateTime.now());
    }

    private void updateGradeAverage() {
        this.gradeAverage =
                (float) this.topicsInfo.stream().
                        mapToDouble(q -> {
                            if(q.getGradeAverage() == null) return 0;
                            else return q.getGradeAverage();
                        }).average().orElse(0);
    }

    private void updateTotalQuestions() {
        this.totalQuestions = this.topicsInfo.stream().mapToLong(TopicInfoVirtualEntity::getTotalQuestions).sum();
    }

    private void updateTotalQuestionsToReview() {
        this.totalQuestionsToReview =
                this.topicsInfo.stream().flatMap(t -> t.getQuestionsInfo().stream()).
                        filter(QuestionInfoVirtualEntity::getIsToReview).count();
    }


}

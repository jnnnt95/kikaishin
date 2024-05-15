package com.nniett.kikaishin.app.persistence.entity.virtual;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.nniett.kikaishin.app.persistence.entity.virtual.QuestionUtils.checkReviewability;
import static com.nniett.kikaishin.app.persistence.entity.virtual.QuestionUtils.getNextReviewDateCalculation;

@Getter
@Setter
public class TopicInfoVirtualEntity {
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
        updateAge();
        updateGradeAverage();
        updateTotalQuestions();
        updateTotalQuestionsToReview();
        updateQuestionIds();
    }

    private void updateQuestionIds() {
        this.questionIds = new ArrayList<>();
        questionsInfo.forEach(q -> questionIds.add(q.getQuestionId()));
    }

    private void updateAge() {
        this.age = ChronoUnit.DAYS.between(createDate, LocalDateTime.now());
    }

    private void updateGradeAverage() {
        this.gradeAverage =
                (float) this.questionsInfo.stream().
                        mapToDouble(q -> {
                            if(q.getGradeAverage() == null) return 0;
                            else return q.getGradeAverage();
                        }).average().orElse(0);
    }

    private void updateTotalQuestions() {
        this.totalQuestions = this.questionsInfo.size();
    }

    private void updateTotalQuestionsToReview() {
        this.totalQuestionsToReview =
                this.questionsInfo.stream().filter(QuestionInfoVirtualEntity::getIsToReview).count();
    }


}

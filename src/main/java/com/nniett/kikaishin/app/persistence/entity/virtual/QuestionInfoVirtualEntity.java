package com.nniett.kikaishin.app.persistence.entity.virtual;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.nniett.kikaishin.app.persistence.entity.virtual.GenericUtils.toLocalDateTime;
import static com.nniett.kikaishin.app.persistence.entity.virtual.QuestionUtils.checkReviewability;
import static com.nniett.kikaishin.app.persistence.entity.virtual.QuestionUtils.getNextReviewDateCalculation;

@Getter
@Setter
public class QuestionInfoVirtualEntity {
    private Integer questionId;
    private Boolean active;
    private Integer totalReviews;
    private Float gradeAverage;
    private Boolean isToReview; // calculated
    private String body;
    private Integer totalAnswers;
    private Integer totalClues;
    private LocalDateTime createDate;
    private Integer lastReviewId;
    private LocalDateTime lastReviewDate;
    private LocalDateTime nextReviewDate; // calculated
    private Integer lastReviewGrade;
    private Float x0;
    private Float x1;
    private Float x2;
    private Integer expectedReviews;
    private Long age; // calculated

    public void calculate() {
        updateIsToReview();
        updateNextReviewDate();
        updateAge();
    }

    private void updateAge() {
        this.age = ChronoUnit.DAYS.between(createDate, LocalDateTime.now());
    }

    private void updateIsToReview() {
        if(nextReviewDate == null) {
            this.nextReviewDate = getNextReviewDateCalculation(
                    lastReviewDate,
                    createDate,
                    totalReviews,
                    x0,
                    x1,
                    x2
            );
        }
        this.isToReview = checkReviewability(nextReviewDate, totalReviews, this.expectedReviews);
    }
    private void updateNextReviewDate() {
        if(this.expectedReviews > totalReviews) {
            this.nextReviewDate = getNextReviewDateCalculation(
                    lastReviewDate,
                    createDate,
                    totalReviews,
                    x0,
                    x1,
                    x2
            );
        } else {
            this.nextReviewDate = toLocalDateTime(new Date(0L));
        }

    }
}

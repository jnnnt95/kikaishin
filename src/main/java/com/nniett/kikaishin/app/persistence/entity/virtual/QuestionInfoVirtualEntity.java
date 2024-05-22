package com.nniett.kikaishin.app.persistence.entity.virtual;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.nniett.kikaishin.app.persistence.entity.virtual.QuestionUtils.checkReviewability;
import static com.nniett.kikaishin.app.persistence.entity.virtual.QuestionUtils.getNextReviewDateCalculation;

@Getter
@Setter
@ToString
public class QuestionInfoVirtualEntity {

    private static final Logger logger = LoggerFactory.getLogger(QuestionInfoVirtualEntity.class);

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
        logger.debug("Executing QuestionInfoVirtualEntity calculate action.");
        logger.debug("Calculate action being executed for QuestionInfoVirtualEntity representation of question id {}.", questionId);
        updateIsToReview();
        updateNextReviewDate();
        updateAge();
    }

    private void updateAge() {
        logger.debug("Updating age.");
        this.age = ChronoUnit.DAYS.between(createDate, LocalDateTime.now());
    }

    private void updateIsToReview() {
        logger.debug("Updating is to review.");
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
        logger.debug("Updating next review date.");
        this.nextReviewDate = getNextReviewDateCalculation(
                lastReviewDate,
                createDate,
                totalReviews,
                x0,
                x1,
                x2
        );
    }
}

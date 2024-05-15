package com.nniett.kikaishin.app.persistence.entity.virtual;

import com.nniett.kikaishin.app.persistence.entity.ReviewModelEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.nniett.kikaishin.app.persistence.entity.virtual.GenericUtils.*;

public abstract class QuestionUtils {

    public static LocalDateTime getNextReviewDateCalculation (
            LocalDateTime mostRecentReviewDate,
            LocalDateTime creationDate,
            Integer reviewCount,
            ReviewModelEntity reviewModel
    ) {
        LocalDateTime checkFrom;
        if(mostRecentReviewDate != null) {
            checkFrom = mostRecentReviewDate;
        } else {
            checkFrom = creationDate;
        }
        long expectedMillis = daysToMilliseconds(expectedDays(reviewCount, reviewModel));
        return checkFrom.plus(expectedMillis, ChronoUnit.MILLIS);
    }



    public static LocalDateTime getNextReviewDateCalculation (
            LocalDateTime mostRecentReviewDate,
            LocalDateTime creationDate,
            Integer reviewCount,
            Float x0, Float x1, Float x2
    ) {
        LocalDateTime checkFrom;
        if(mostRecentReviewDate != null) {
            checkFrom = mostRecentReviewDate;
        } else {
            checkFrom = creationDate;
        }
        long expectedMillis = daysToMilliseconds(expectedDays(reviewCount, x0, x1, x2));
        return checkFrom.plus(expectedMillis, ChronoUnit.MILLIS);
    }

    public static float expectedDays(Integer reviewCount, ReviewModelEntity reviewModel) {
        int x = reviewCount + 1;
        return reviewModel.calculate(x);
    }

    public static float expectedDays(Integer reviewCount, Float x0, Float x1, Float x2) {
        int x = reviewCount + 1;
        return ReviewModelEntity.calculate(x, x0, x1, x2);
    }

    public static boolean checkReviewability (
            LocalDateTime nextReviewDate,
            Integer reviewsSoFar,
            Integer totalExpectedReviews
    ) {
        //total expected reviews being null means no limit for next review.
        if(totalExpectedReviews == null) {
            return LocalDateTime.now().isAfter(nextReviewDate);
        } else {
            return (reviewsSoFar < totalExpectedReviews) && LocalDateTime.now().isAfter(nextReviewDate);
        }

    }
}

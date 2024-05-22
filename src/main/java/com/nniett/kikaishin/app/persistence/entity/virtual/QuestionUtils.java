package com.nniett.kikaishin.app.persistence.entity.virtual;

import com.nniett.kikaishin.app.persistence.entity.ReviewModelEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.nniett.kikaishin.app.persistence.entity.virtual.GenericUtils.*;

public abstract class QuestionUtils {

    private static final Logger logger = LoggerFactory.getLogger(QuestionUtils.class);

    public static LocalDateTime getNextReviewDateCalculation (
            LocalDateTime mostRecentReviewDate,
            LocalDateTime creationDate,
            Integer reviewCount,
            ReviewModelEntity reviewModel
    ) {
        logger.debug("Calculating question next review date with review model.");
        LocalDateTime checkFrom;
        if(mostRecentReviewDate != null) {
            checkFrom = mostRecentReviewDate;
        } else {
            logger.debug("No reviews performed. Using creation date.");
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
        logger.debug("Calculating question next review date with raw x values.");
        LocalDateTime checkFrom;
        if(mostRecentReviewDate != null) {
            checkFrom = mostRecentReviewDate;
        } else {
            logger.debug("No reviews performed. Using creation date.");
            checkFrom = creationDate;
        }
        long expectedMillis = daysToMilliseconds(expectedDays(reviewCount, x0, x1, x2));
        return checkFrom.plus(expectedMillis, ChronoUnit.MILLIS);
    }

    public static float expectedDays(Integer reviewCount, ReviewModelEntity reviewModel) {
        logger.debug("Calculating expected days.");
        int x = reviewCount + 1;
        return reviewModel.calculate(x);
    }

    public static float expectedDays(Integer reviewCount, Float x0, Float x1, Float x2) {
        logger.debug("Calculating expected days.");
        int x = reviewCount + 1;
        return ReviewModelEntity.calculate(x, x0, x1, x2);
    }

    public static boolean checkReviewability (
            LocalDateTime nextReviewDate,
            Integer reviewsSoFar,
            Integer totalExpectedReviews
    ) {
        logger.debug("Checking question reviewability.");
        //total expected reviews being null means no limit for next review.
        if(totalExpectedReviews == null) {
            logger.debug("Question is always to review.");
            return LocalDateTime.now().isAfter(nextReviewDate);
        } else {
            return
                    // are expected reviews completed?
                    (reviewsSoFar < totalExpectedReviews)
                            &&
                            // is next review date in the past?
                            LocalDateTime.now().isAfter(nextReviewDate);
        }

    }
}

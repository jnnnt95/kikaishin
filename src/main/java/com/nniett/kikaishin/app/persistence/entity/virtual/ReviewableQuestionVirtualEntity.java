package com.nniett.kikaishin.app.persistence.entity.virtual;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewModelEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.nniett.kikaishin.app.persistence.entity.virtual.QuestionUtils.*;

@Getter
@Setter
@ToString
public class ReviewableQuestionVirtualEntity implements Comparable<ReviewableQuestionVirtualEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ReviewableQuestionVirtualEntity.class);

    // Self properties
    private Integer questionId;
    private String body;
    private Integer reviewCount;
    private Integer mostRecentReviewId; // can be null
    private LocalDateTime mostRecentReviewDate; // can be null
    private String lookupKey;
    private LocalDateTime creationDate;

    //to calculate
    private LocalDateTime nextReviewDate;

    //Entities properties
    private List<AnswerEntity> answers;
    private List<ClueEntity> clues;
    private ReviewModelEntity reviewModel;

    public boolean isToReview() {
        if(nextReviewDate == null) {
            calculateNextReviewDate();
        }
        return checkReviewability(nextReviewDate, reviewCount, reviewModel.getExpectedReviews());
    }

    public void calculateNextReviewDate() {
        this.nextReviewDate = getNextReviewDateCalculation(
                this.mostRecentReviewDate,
                this.creationDate,
                this.reviewCount,
                this.reviewModel
        );
    }

    @Override
    public int compareTo(@NonNull ReviewableQuestionVirtualEntity _other) {
        logger.trace("Comparing question {} vs {}.", this.questionId, _other.getQuestionId());
        logger.trace("Question dates to compare {} vs {}.", this.nextReviewDate, _other.getNextReviewDate());
        ReviewableQuestionVirtualEntity _this = this;
        int thisIsSmaller = -1;
        int thisIsEqual = 0;
        int thisIsBigger = 1;

        if(this.nextReviewDate == null) {
            calculateNextReviewDate();
        }

        long diff = ChronoUnit.MILLIS.between(_this.getNextReviewDate(), _other.getNextReviewDate());

        if(diff < 0) {
            logger.trace("Question id {} is bigger.", this.questionId);
            return thisIsBigger;
        } else if(diff > 0) {
            logger.trace("Question id {} is bigger.", _other.getQuestionId());
            return thisIsSmaller;
        } else {
            logger.trace("Questions are equal. Ids {} and {}.", this.questionId, _other.getQuestionId());
            return thisIsEqual;
        }

    }
}

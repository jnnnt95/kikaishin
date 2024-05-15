package com.nniett.kikaishin.app.persistence.entity.virtual;

import com.nniett.kikaishin.app.persistence.entity.AnswerEntity;
import com.nniett.kikaishin.app.persistence.entity.ClueEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewModelEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.nniett.kikaishin.app.persistence.entity.virtual.QuestionUtils.*;

@Getter
@Setter
public class ReviewableQuestionVirtualEntity implements Comparable<ReviewableQuestionVirtualEntity> {

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
        ReviewableQuestionVirtualEntity _this = this;
        int thisIsSmaller = -1;
        int thisIsEqual = 0;
        int thisIsBigger = 1;

        if(this.nextReviewDate == null) {
            calculateNextReviewDate();
        }

        long diff = ChronoUnit.MILLIS.between(_this.getNextReviewDate(), _other.getNextReviewDate());

        if(diff < 0) {
            return thisIsBigger;
        } else if(diff > 0) {
            return thisIsSmaller;
        } else {
            return thisIsEqual;
        }

    }
}

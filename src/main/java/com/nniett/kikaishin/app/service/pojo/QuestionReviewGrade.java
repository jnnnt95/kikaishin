package com.nniett.kikaishin.app.service.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class QuestionReviewGrade implements Pojo<Integer> {
    @JsonIgnore
    private Integer questionReviewGradeId;
    private Integer questionId;
    @JsonIgnore
    private Integer reviewId;
    private Integer gradeValue;
    @JsonIgnore
    private Question parentQuestion;
    @JsonIgnore
    private Review parentReview;

    @JsonIgnore
    @Override
    public Integer getPK() {
        return this.questionReviewGradeId;
    }
}

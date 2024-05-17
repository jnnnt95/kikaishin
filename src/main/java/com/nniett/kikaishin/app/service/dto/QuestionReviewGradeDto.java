package com.nniett.kikaishin.app.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.common.Pojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionReviewGradeDto implements Pojo<Integer> {
    @JsonIgnore
    private Integer questionReviewGradeId;
    private Integer questionId;
    @JsonIgnore
    private Integer reviewId;
    private Integer gradeValue;
    @JsonIgnore
    private QuestionDto parentQuestion;
    @JsonIgnore
    private ReviewDto parentReview;

    @JsonIgnore
    @Override
    public Integer getPK() {
        return this.questionReviewGradeId;
    }
}

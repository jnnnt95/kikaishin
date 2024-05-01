package com.nniett.kikaishin.web.service.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class QuestionReviewGrade {
    private Integer questionReviewGradeId;
    private Integer questionId;
    private Integer reviewId;
    private Integer gradeValue;
    private Question parentQuestion;
    private Review parentReview;
}

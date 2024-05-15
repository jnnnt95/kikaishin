package com.nniett.kikaishin.app.service.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionInfo {
    private Integer questionId;
    private Boolean active;
    private LocalDateTime nextReviewDate;
    private Long age;
    private Integer totalReviews;
    private Float gradeAverage;
    private Boolean isToReview;
    private String body;
    private Integer totalAnswers;
    private Integer totalClues;
    private LocalDateTime createDate;
    private Integer lastReviewId;
    private LocalDateTime lastReviewDate;
    private Integer lastReviewGrade;
}

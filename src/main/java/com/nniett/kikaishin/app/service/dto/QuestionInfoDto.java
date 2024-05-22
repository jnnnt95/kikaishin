package com.nniett.kikaishin.app.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class QuestionInfoDto {
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

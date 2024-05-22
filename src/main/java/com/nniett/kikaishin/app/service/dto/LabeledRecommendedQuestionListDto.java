package com.nniett.kikaishin.app.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LabeledRecommendedQuestionListDto {
    private String label;
    private int totalQuestions;
    private List<RecommendedQuestionDto> recommendedQuestions;
}

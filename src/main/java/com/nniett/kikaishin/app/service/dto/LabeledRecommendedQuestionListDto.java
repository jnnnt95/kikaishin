package com.nniett.kikaishin.app.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LabeledRecommendedQuestionListDto {
    private String label;
    private int totalQuestions;
    private List<RecommendedQuestionDto> recommendedQuestions;
}

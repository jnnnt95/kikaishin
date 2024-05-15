package com.nniett.kikaishin.app.service.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LabeledRecommendedQuestionList {
    private String label;
    private int totalQuestions;
    private List<RecommendedQuestion> recommendedQuestions;
}

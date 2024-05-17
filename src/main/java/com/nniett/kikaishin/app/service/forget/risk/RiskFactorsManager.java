package com.nniett.kikaishin.app.service.forget.risk;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.service.dto.LabeledRecommendedQuestionListDto;

import java.util.ArrayList;
import java.util.List;

import com.nniett.kikaishin.app.service.dto.RecommendedQuestionDto;

public final class RiskFactorsManager {



    private RiskFactorsManager() {}

    public static List<LabeledRecommendedQuestionListDto> getRecommendedReview(List<QuestionEntity> questions) {
        List<LabeledRecommendedQuestionListDto> levels = new ArrayList<>();

        LabeledRecommendedQuestionListDto highLevel = new LabeledRecommendedQuestionListDto();
        highLevel.setLabel("High");
        highLevel.setRecommendedQuestions(new ArrayList<>());
        levels.add(highLevel);

        LabeledRecommendedQuestionListDto mediumLevel = new LabeledRecommendedQuestionListDto();
        mediumLevel.setLabel("Medium");
        mediumLevel.setRecommendedQuestions(new ArrayList<>());
        levels.add(mediumLevel);

        LabeledRecommendedQuestionListDto lowLevel = new LabeledRecommendedQuestionListDto();
        lowLevel.setLabel("Low");
        lowLevel.setRecommendedQuestions(new ArrayList<>());
        levels.add(lowLevel);

        processQuestions(questions, highLevel, mediumLevel, lowLevel);

        highLevel.setTotalQuestions(highLevel.getRecommendedQuestions().size());
        mediumLevel.setTotalQuestions(mediumLevel.getRecommendedQuestions().size());
        lowLevel.setTotalQuestions(lowLevel.getRecommendedQuestions().size());

        return levels;
    }

    private static void processQuestions(
            List<QuestionEntity> questions,
            LabeledRecommendedQuestionListDto _high,
            LabeledRecommendedQuestionListDto _medium,
            LabeledRecommendedQuestionListDto _low) {

        questions.forEach(q -> {
            RecommendedQuestionDto result = processQuestion(q);
            switch(result.getClassifier()) {
                case low -> {
                    _low.getRecommendedQuestions().add(result);
                    result.setQuestionId(q.getId());
                }
                case medium -> {
                    _medium.getRecommendedQuestions().add(result);
                    result.setQuestionId(q.getId());
                }
                case high -> {
                    _high.getRecommendedQuestions().add(result);
                    result.setQuestionId(q.getId());
                }
                case none -> {}
            }
        });

    }

    private static RecommendedQuestionDto processQuestion(QuestionEntity question) {
        List<RecommendedQuestionDto> risks = new ArrayList<>();

        risks.add(RiskFactorsClassifiers.byLowAverage.apply(question));
        risks.add(RiskFactorsClassifiers.byPerformanceDecrease.apply(question));
        risks.add(RiskFactorsClassifiers.byLastReviewGrade.apply(question));
        risks.add(RiskFactorsClassifiers.byAgingReview.apply(question));

        RecommendedQuestionDto higherRisk = risks.get(0);

        for(RecommendedQuestionDto recommendedQuestion: risks) {
            if(recommendedQuestion.getClassifier().isHigher(higherRisk.getClassifier())) {
                higherRisk = recommendedQuestion;
            }
        }

        return higherRisk;
    }
}

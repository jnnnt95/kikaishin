package com.nniett.kikaishin.app.service.forget.risk;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.service.pojo.LabeledRecommendedQuestionList;

import java.util.ArrayList;
import java.util.List;

import com.nniett.kikaishin.app.service.pojo.RecommendedQuestion;

public final class RiskFactorsManager {



    private RiskFactorsManager() {}

    public static List<LabeledRecommendedQuestionList> getRecommendedReview(List<QuestionEntity> questions) {
        List<LabeledRecommendedQuestionList> levels = new ArrayList<>();

        LabeledRecommendedQuestionList highLevel = new LabeledRecommendedQuestionList();
        highLevel.setLabel("High");
        highLevel.setRecommendedQuestions(new ArrayList<>());
        levels.add(highLevel);

        LabeledRecommendedQuestionList mediumLevel = new LabeledRecommendedQuestionList();
        mediumLevel.setLabel("Medium");
        mediumLevel.setRecommendedQuestions(new ArrayList<>());
        levels.add(mediumLevel);

        LabeledRecommendedQuestionList lowLevel = new LabeledRecommendedQuestionList();
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
            LabeledRecommendedQuestionList _high,
            LabeledRecommendedQuestionList _medium,
            LabeledRecommendedQuestionList _low) {

        questions.forEach(q -> {
            RecommendedQuestion result = processQuestion(q);
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

    private static RecommendedQuestion processQuestion(QuestionEntity question) {
        List<RecommendedQuestion> risks = new ArrayList<>();

        risks.add(RiskFactorsClassifiers.byLowAverage.apply(question));
        risks.add(RiskFactorsClassifiers.byPerformanceDecrease.apply(question));
        risks.add(RiskFactorsClassifiers.byLastReviewGrade.apply(question));
        risks.add(RiskFactorsClassifiers.byAgingReview.apply(question));

        RecommendedQuestion higherRisk = risks.get(0);

        for(RecommendedQuestion recommendedQuestion: risks) {
            if(recommendedQuestion.getClassifier().isHigher(higherRisk.getClassifier())) {
                higherRisk = recommendedQuestion;
            }
        }

        return higherRisk;
    }
}

package com.nniett.kikaishin.app.service.forget.risk;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.service.dto.LabeledRecommendedQuestionListDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.nniett.kikaishin.app.service.dto.RecommendedQuestionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RiskFactorsManager {

    // TODO:
    //      This class is not isolating properly persistence layer:
    //              Raw use of QuestionEntity class

    private static final Logger logger = LoggerFactory.getLogger(RiskFactorsManager.class);
    private static final String HIGH_VAL = "High";
    private static final String MEDIUM_VAL = "Medium";
    private static final String LOW_VAL = "Low";

    private RiskFactorsManager() {}

    public static List<LabeledRecommendedQuestionListDto> getRecommendedReview(List<QuestionEntity> questions) {
        logger.trace("Calculating recommendation status for total {} questions.", questions.size());
        List<LabeledRecommendedQuestionListDto> levels = new ArrayList<>();

        logger.debug("Initializing risk lists.");
        LabeledRecommendedQuestionListDto highLevel = new LabeledRecommendedQuestionListDto();
        highLevel.setLabel(HIGH_VAL);
        highLevel.setRecommendedQuestions(new ArrayList<>());
        levels.add(highLevel);

        LabeledRecommendedQuestionListDto mediumLevel = new LabeledRecommendedQuestionListDto();
        mediumLevel.setLabel(MEDIUM_VAL);
        mediumLevel.setRecommendedQuestions(new ArrayList<>());
        levels.add(mediumLevel);

        LabeledRecommendedQuestionListDto lowLevel = new LabeledRecommendedQuestionListDto();
        lowLevel.setLabel(LOW_VAL);
        lowLevel.setRecommendedQuestions(new ArrayList<>());
        levels.add(lowLevel);

        processQuestions(questions, highLevel, mediumLevel, lowLevel);

        highLevel.setTotalQuestions(highLevel.getRecommendedQuestions().size());
        mediumLevel.setTotalQuestions(mediumLevel.getRecommendedQuestions().size());
        lowLevel.setTotalQuestions(lowLevel.getRecommendedQuestions().size());

        logger.debug("Clearing empty risk lists.");
        return levels.stream().filter(l -> l.getTotalQuestions() > 0).collect(Collectors.toList());
    }

    private static void processQuestions(
            List<QuestionEntity> questions,
            LabeledRecommendedQuestionListDto _high,
            LabeledRecommendedQuestionListDto _medium,
            LabeledRecommendedQuestionListDto _low) {
        logger.debug("Processing questions by risk.");
        questions.forEach(q -> {
            RecommendedQuestionDto result = processQuestion(q);
            switch(result.getClassifier()) {
                case low -> {
                    logger.debug("Question evaluated as low risk.");
                    logger.debug("Question id: {}.", q.getId());
                    _low.getRecommendedQuestions().add(result);
                    result.setQuestionId(q.getId());
                }
                case medium -> {
                    logger.debug("Question evaluated as medium risk.");
                    logger.debug("Question id: {}.", q.getId());
                    _medium.getRecommendedQuestions().add(result);
                    result.setQuestionId(q.getId());
                }
                case high -> {
                    logger.debug("Question evaluated as high risk.");
                    logger.debug("Question id: {}.", q.getId());
                    _high.getRecommendedQuestions().add(result);
                    result.setQuestionId(q.getId());
                }
                case none -> {
                    logger.debug("Question evaluated as no risk.");
                    logger.debug("Question id: {}.", q.getId());
                }
            }
        });

    }

    private static RecommendedQuestionDto processQuestion(QuestionEntity question) {
        logger.debug("Evaluating question risk.");
        logger.trace("Evaluation on question id {}.", question.getId());
        List<RecommendedQuestionDto> risks = new ArrayList<>();

        risks.add(RiskFactorsClassifiers.byLowAverage.apply(question));
        risks.add(RiskFactorsClassifiers.byPerformanceDecrease.apply(question));
        risks.add(RiskFactorsClassifiers.byLastReviewGrade.apply(question));
        risks.add(RiskFactorsClassifiers.byAgingReview.apply(question));

        RecommendedQuestionDto higherRisk = risks.get(0);

        logger.debug("Determining question highest risk.");
        for(RecommendedQuestionDto recommendedQuestion: risks) {
            if(recommendedQuestion.getClassifier().isHigher(higherRisk.getClassifier())) {
                higherRisk = recommendedQuestion;
            }
        }

        logger.trace("Question highest risk reason {} with level of {}.", higherRisk.getReason(), higherRisk.getClassifier());
        return higherRisk;
    }
}

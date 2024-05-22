package com.nniett.kikaishin.app.service.forget.risk;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewModelEntity;
import com.nniett.kikaishin.app.service.dto.RecommendedQuestionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public final class RiskFactorsClassifiers {

    // TODO:
    //      This class is not isolating properly persistence layer:
    //              Raw use of QuestionEntity class

    private static final Logger logger = LoggerFactory.getLogger(RiskFactorsClassifiers.class);

    private RiskFactorsClassifiers() {}

    public enum Classifier {
        high,
        medium,
        low,
        none;

        public boolean isHigher(Classifier other) {
            logger.debug("Comparing classifiers.");
            logger.trace("Classifiers being compared: {} vs {}.", this, other);
            if(this == other) {
                return false;
            }
            switch(this) {
                case none -> {
                    return false;
                }
                case low -> {
                    return other == none;
                }
                case medium -> {
                    return other == none || other == low;
                }
                case high -> {
                    return true;
                }
                default -> throw new RuntimeException("Not supported option");
            }
        }
    }


    public static final Function<QuestionEntity, RecommendedQuestionDto> byLowAverage = q ->
    {
        logger.debug("Evaluating question risk byLowAverage.");
        logger.trace("Evaluating question risk for question id {}.", q.getId());
        double average = getQuestionLastFiveReviewsAverage(q);
        logger.trace("Question average calculated {}.", average);

        RecommendedQuestionDto recommendedQuestion = new RecommendedQuestionDto();
        recommendedQuestion.setReason("Last five grades average too low.");

        if(average < 3) {
            recommendedQuestion.setClassifier(Classifier.high);
            logger.trace("Question risk classified as {}.", Classifier.high);
            return recommendedQuestion;
        } else if(average >= 3 && average < 3.5) {
            logger.trace("Question risk classified as {}.", Classifier.medium);
            recommendedQuestion.setClassifier(Classifier.medium);
            return recommendedQuestion;
        } else if(average >= 3.5 && average < 4) {
            logger.trace("Question risk classified as {}.", Classifier.low);
            recommendedQuestion.setClassifier(Classifier.low);
            return recommendedQuestion;
        } else {
            logger.trace("Question risk classified as {}.", Classifier.none);
            recommendedQuestion.setClassifier(Classifier.none);
            return recommendedQuestion;
        }
    };

    private static double getQuestionLastFiveReviewsAverage(QuestionEntity question) {
        logger.debug("Calculating question last five reviews average.");
        List<QuestionReviewGradeEntity> questionReviews = question.getQuestionReviewGrades();
        if(!questionReviews.isEmpty()) {
            if(questionReviews.size() >= 5) {
                questionReviews = questionReviews.subList(0, 5);
                logger.debug("Average is being calculated.");
                return questionReviews.stream().mapToInt(QuestionReviewGradeEntity::getGradeValue).average().orElse(0);
            } else {
                logger.debug("Not enough reviews performed for risk review.");
                return 5;
            }
        } else {
            logger.debug("No reviews performed for risk review.");
            return 5;
        }
    }

    public static final Function<QuestionEntity, RecommendedQuestionDto> byPerformanceDecrease = q ->
    {
        logger.debug("Evaluating question risk byPerformanceDecrease.");
        logger.trace("Evaluating question risk for question id {}.", q.getId());
        RecommendedQuestionDto recommendedQuestion = new RecommendedQuestionDto();
        recommendedQuestion.setReason("Last two grades evidence performance decrease.");

        Integer grade1 = getQuestionLastGrade(q);
        Integer grade2 = grade1 != null ? getQuestionPreviousToLastGrade(q) : null;

        logger.trace("Last grade {}.", grade1);
        logger.trace("Previous to last grade {}.", grade2);

        if(grade1 != null && grade2 != null) {
            int diff = grade1 - grade2;
            logger.trace("Difference between grades {}.", diff);

            if(diff <= -3) {
                recommendedQuestion.setClassifier(Classifier.high);
                logger.trace("Question risk classified as {}.", Classifier.high);
                return recommendedQuestion;
            } else if(diff == -2) {
                logger.trace("Question risk classified as {}.", Classifier.medium);
                recommendedQuestion.setClassifier(Classifier.medium);
                return recommendedQuestion;
            } else if(diff == -1) {
                logger.trace("Question risk classified as {}.", Classifier.low);
                recommendedQuestion.setClassifier(Classifier.low);
                return recommendedQuestion;
            } else {
                logger.trace("Question risk classified as {}.", Classifier.none);
                recommendedQuestion.setClassifier(Classifier.none);
                return recommendedQuestion;
            }
        } else {
            logger.trace("Question risk classified as {}.", Classifier.none);
            recommendedQuestion.setClassifier(Classifier.none);
            return recommendedQuestion;
        }
    };

    private static Integer getQuestionLastGrade(QuestionEntity question) {
        logger.debug("Retrieving question last grade.");
        logger.trace("Last grade being retrieved for question id {}.", question.getId());
        List<QuestionReviewGradeEntity> questionReviews = question.getQuestionReviewGrades();
        if(!questionReviews.isEmpty()) {
            return questionReviews.get(questionReviews.size() - 1).getGradeValue();
        } else {
            logger.debug("Last grade not found.");
            return null;
        }
    }

    private static Integer getQuestionPreviousToLastGrade(QuestionEntity question) {
        logger.debug("Retrieving question previous to last grade.");
        logger.trace("Previous to last grade being retrieved for question id {}.", question.getId());
        List<QuestionReviewGradeEntity> questionReviews = question.getQuestionReviewGrades();
        if(!questionReviews.isEmpty()) {
            if(questionReviews.size() > 1) {
                return questionReviews.get(questionReviews.size() - 2).getGradeValue();
            } else {
                logger.debug("Previous to last grade not found.");
                return null;
            }
        } else {
            logger.debug("Previous to last grade not found.");
            return null;
        }
    }

    public static final Function<QuestionEntity, RecommendedQuestionDto> byLastReviewGrade = q ->
    {
        logger.debug("Evaluating question risk byLastReviewGrade.");
        logger.trace("Evaluating question risk for question id {}.", q.getId());
        RecommendedQuestionDto recommendedQuestion = new RecommendedQuestionDto();
        recommendedQuestion.setReason("Last review grade is too low.");

        Integer grade = getQuestionLastGrade(q);
        logger.trace("Last review grade {}.", grade);

        if (grade != null) {
            if(grade <= 2) {
                logger.trace("Question risk classified as {}.", Classifier.high);
                recommendedQuestion.setClassifier(Classifier.high);
                return recommendedQuestion;
            } else if(grade == 3) {
                logger.trace("Question risk classified as {}.", Classifier.medium);
                recommendedQuestion.setClassifier(Classifier.medium);
                return recommendedQuestion;
            } else if(grade == 4) {
                logger.trace("Question risk classified as {}.", Classifier.low);
                recommendedQuestion.setClassifier(Classifier.low);
                return recommendedQuestion;
            } else {
                logger.trace("Question risk classified as {}.", Classifier.none);
                recommendedQuestion.setClassifier(Classifier.none);
                return recommendedQuestion;
            }
        } else {
            logger.debug("No reviews performed for this question.");
            logger.trace("Question risk classified as {}.", Classifier.none);
            recommendedQuestion.setClassifier(Classifier.none);
            return recommendedQuestion;
        }
    };

    public static final Function<QuestionEntity, RecommendedQuestionDto> byAgingReview = q ->
    {
        logger.debug("Evaluating question risk byAgingReview.");
        logger.trace("Evaluating question risk for question id {}.", q.getId());
        RecommendedQuestionDto recommendedQuestion = new RecommendedQuestionDto();
        recommendedQuestion.setReason("Last review is too old.");

        long totalDays = getTotalDaysFromLastReview(q);
        int nextReviewCount = q.getQuestionReviewGrades().size() + 1;
        ReviewModelEntity questionReviewModel = q.getReviewModel();
        float totalExpectedDays = questionReviewModel.calculate(nextReviewCount);

        logger.trace("Total days from last review {}.", totalDays);
        logger.trace("Total expected days for next review {}.", totalExpectedDays);

        if(totalDays >= 15) {
            if (totalExpectedDays >= 15) {
                logger.trace("Question risk classified as {}.", Classifier.none);
                recommendedQuestion.setClassifier(Classifier.none);
            } else {
                logger.trace("Question risk classified as {}.", Classifier.high);
                recommendedQuestion.setClassifier(Classifier.high);
            }
            return  recommendedQuestion;
        } else if(totalDays >= 11) {
            if (totalExpectedDays >= 11) {
                logger.trace("Question risk classified as {}.", Classifier.none);
                recommendedQuestion.setClassifier(Classifier.none);
            } else {
                logger.trace("Question risk classified as {}.", Classifier.medium);
                recommendedQuestion.setClassifier(Classifier.medium);
            }
            return  recommendedQuestion;
        } else if(totalDays >= 7) {
            if (totalExpectedDays >= 7) {
                logger.trace("Question risk classified as {}.", Classifier.none);
                recommendedQuestion.setClassifier(Classifier.none);
            } else {
                logger.trace("Question risk classified as {}.", Classifier.low);
                recommendedQuestion.setClassifier(Classifier.low);
            }
            return  recommendedQuestion;
        } else {
            logger.trace("Question risk classified as {}.", Classifier.none);
            recommendedQuestion.setClassifier(Classifier.none);
            return recommendedQuestion;
        }
    };

    private static Long getTotalDaysFromLastReview(QuestionEntity question) {
        logger.debug("Calculating question total days from last review.");
        List<QuestionReviewGradeEntity> questionReviews = question.getQuestionReviewGrades();
        LocalDateTime dateToCheck;
        if(!question.getQuestionReviewGrades().isEmpty()) {
            dateToCheck = questionReviews.get(questionReviews.size() - 1).getCreationDate();
        } else {
            logger.debug("No reviews performed. Evaluating on question creation date.");
            dateToCheck = question.getCreationDate();
        }
        return ChronoUnit.DAYS.between(dateToCheck, LocalDateTime.now());
    }
}

package com.nniett.kikaishin.app.service.forget.risk;

import com.nniett.kikaishin.app.persistence.entity.QuestionEntity;
import com.nniett.kikaishin.app.persistence.entity.QuestionReviewGradeEntity;
import com.nniett.kikaishin.app.persistence.entity.ReviewModelEntity;
import com.nniett.kikaishin.app.service.dto.RecommendedQuestionDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public final class RiskFactorsClassifiers {

    private RiskFactorsClassifiers(){}

    public enum Classifier {
        high,
        medium,
        low,
        none;

        public boolean isHigher(Classifier other) {
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
        double average = getQuestionLastFiveReviewsAverage(q);
        RecommendedQuestionDto recommendedQuestion = new RecommendedQuestionDto();
        recommendedQuestion.setReason("Last five grades average too low.");

        if(average < 3) {
            recommendedQuestion.setClassifier(Classifier.high);
            return recommendedQuestion;
        } else if(average >= 3 && average < 3.5) {
            recommendedQuestion.setClassifier(Classifier.medium);
            return recommendedQuestion;
        } else if(average >= 3.5 && average < 4) {
            recommendedQuestion.setClassifier(Classifier.low);
            return recommendedQuestion;
        } else {
            recommendedQuestion.setClassifier(Classifier.none);
            return recommendedQuestion;
        }
    };

    private static double getQuestionLastFiveReviewsAverage(QuestionEntity question) {
        List<QuestionReviewGradeEntity> questionReviews = question.getQuestionReviewGrades();
        if(!questionReviews.isEmpty()) {
            if(questionReviews.size() > 5) {
                questionReviews = questionReviews.subList(0, 5);
            }
            return questionReviews.stream().mapToInt(QuestionReviewGradeEntity::getGradeValue).average().orElse(0);
        } else {
            return 0;
        }
    }

    public static final Function<QuestionEntity, RecommendedQuestionDto> byPerformanceDecrease = q ->
    {
        RecommendedQuestionDto recommendedQuestion = new RecommendedQuestionDto();
        recommendedQuestion.setReason("Last two grades evidence performance decrease.");

        Integer grade1 = getQuestionLastGrade(q);
        Integer grade2 = getQuestionPreviousToLastGrade(q);

        if(grade1 != null && grade2 != null) {
            int diff = grade1 - grade2;

            if(diff <= -3) {
                recommendedQuestion.setClassifier(Classifier.high);
                return recommendedQuestion;
            } else if(diff == -2) {
                recommendedQuestion.setClassifier(Classifier.medium);
                return recommendedQuestion;
            } else if(diff == -1) {
                recommendedQuestion.setClassifier(Classifier.low);
                return recommendedQuestion;
            } else {
                recommendedQuestion.setClassifier(Classifier.none);
                return recommendedQuestion;
            }
        } else {
            recommendedQuestion.setClassifier(Classifier.none);
            return recommendedQuestion;
        }
    };

    private static Integer getQuestionLastGrade(QuestionEntity question) {
        List<QuestionReviewGradeEntity> questionReviews = question.getQuestionReviewGrades();
        if(!questionReviews.isEmpty()) {
            return questionReviews.get(questionReviews.size() - 1).getGradeValue();
        } else {
            return null;
        }
    }

    private static Integer getQuestionPreviousToLastGrade(QuestionEntity question) {
        List<QuestionReviewGradeEntity> questionReviews = question.getQuestionReviewGrades();
        if(!questionReviews.isEmpty()) {
            if(questionReviews.size() > 1) {
                return questionReviews.get(questionReviews.size() - 2).getGradeValue();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static final Function<QuestionEntity, RecommendedQuestionDto> byLastReviewGrade = q ->
    {
        RecommendedQuestionDto recommendedQuestion = new RecommendedQuestionDto();
        recommendedQuestion.setReason("Last review grade is too low.");

        Integer grade = getQuestionLastGrade(q);

        if (grade != null) {
            if(grade <= 2) {
                recommendedQuestion.setClassifier(Classifier.high);
                return recommendedQuestion;
            } else if(grade == 3) {
                recommendedQuestion.setClassifier(Classifier.medium);
                return recommendedQuestion;
            } else if(grade == 4) {
                recommendedQuestion.setClassifier(Classifier.low);
                return recommendedQuestion;
            } else {
                recommendedQuestion.setClassifier(Classifier.none);
                return recommendedQuestion;
            }
        } else {
            recommendedQuestion.setClassifier(Classifier.none);
            return recommendedQuestion;
        }
    };

    public static final Function<QuestionEntity, RecommendedQuestionDto> byAgingReview = q ->
    {
        RecommendedQuestionDto recommendedQuestion = new RecommendedQuestionDto();
        recommendedQuestion.setReason("Last review is too old.");

        long totalDays = getTotalDaysFromLastReview(q);
        ReviewModelEntity questionReviewModel = q.getReviewModel();
        int nextReviewCount = q.getQuestionReviewGrades().size() + 1;

        if(totalDays >= 15) {
            if (questionReviewModel.calculate(nextReviewCount) >= 15) {
                recommendedQuestion.setClassifier(Classifier.none);
            } else {
                recommendedQuestion.setClassifier(Classifier.high);
            }
            return  recommendedQuestion;
        } else if(totalDays >= 11) {
            if (questionReviewModel.calculate(nextReviewCount) >= 11) {
                recommendedQuestion.setClassifier(Classifier.none);
            } else {
                recommendedQuestion.setClassifier(Classifier.medium);
            }
            return  recommendedQuestion;
        } else if(totalDays >= 7) {
            if (questionReviewModel.calculate(nextReviewCount) >= 7) {
                recommendedQuestion.setClassifier(Classifier.none);
            } else {
                recommendedQuestion.setClassifier(Classifier.low);
            }
            return  recommendedQuestion;
        } else {
            recommendedQuestion.setClassifier(Classifier.none);
            return recommendedQuestion;
        }
    };

    private static Long getTotalDaysFromLastReview(QuestionEntity question) {
        List<QuestionReviewGradeEntity> questionReviews = question.getQuestionReviewGrades();
        LocalDateTime dateToCheck;
        if(!question.getQuestionReviewGrades().isEmpty()) {
            dateToCheck = questionReviews.get(questionReviews.size() - 1).getCreationDate();
        } else {
            dateToCheck = question.getCreationDate();
        }
        return ChronoUnit.DAYS.between(dateToCheck, LocalDateTime.now());
    }
}

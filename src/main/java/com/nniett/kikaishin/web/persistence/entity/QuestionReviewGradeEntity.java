package com.nniett.kikaishin.web.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "question_review_grade")
@Getter
@Setter
@NoArgsConstructor
public class QuestionReviewGradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, name = "question_fk")
    private Integer questionId;

    @Column(nullable = false, name = "review_fk")
    private Integer reviewId;

    @Column(nullable = false, name = "grade_value")
    private Integer gradeValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "question_review_grade_question_fk"),
            name = "question_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private QuestionEntity question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "question_review_grade_review_fk"),
            name = "review_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private ReviewEntity review;
}

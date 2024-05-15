package com.nniett.kikaishin.app.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.persistence.entity.construction.ImmutableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "question_review_grade")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class QuestionReviewGradeEntity extends ImmutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "question_fk")
    private Integer questionId;

    @Column(name = "review_fk")
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

package com.nniett.kikaishin.web.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "review_model")
@Getter
@Setter
@NoArgsConstructor
public class ReviewModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, name = "question_fk", unique = true)
    private Integer questionId;

    @Column(nullable = false)
    private Float x0;

    @Column
    private Float x1;

    @Column
    private Float x2;

    @Column(name = "expected_reviews")
    private Integer expectedReviews;

    @OneToOne
    @JoinColumn(
            foreignKey = @ForeignKey(name = "review_model_question_fk"),
            name = "question_fk",
            referencedColumnName = "id",
            updatable = false,
            insertable = false)
    @JsonIgnore
    private QuestionEntity question;
}

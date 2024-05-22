package com.nniett.kikaishin.app.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "review_model")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private Float x0;

    @Column
    private Float x1;

    @Column
    private Float x2;

    @Column(name = "expected_reviews")
    private Integer expectedReviews;

    @OneToOne
    @MapsId
    @JoinColumn(
            foreignKey = @ForeignKey(name = "review_model_question_fk"),
            name = "question_fk",
            referencedColumnName = "id",
            updatable = false,
            insertable = false,
            unique = true)
    @JsonIgnore
    private QuestionEntity question;

    public float calculate(float x) {
        return calculate(x, x0, x1, x2);
    }
    public static float calculate(float x, float x0, float x1, float x2) {
        return x0 + x1*x + x2*x*x;
    }
}

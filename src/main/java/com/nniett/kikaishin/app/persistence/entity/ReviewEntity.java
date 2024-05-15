package com.nniett.kikaishin.app.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.persistence.entity.construction.ImmutableEntity;
import com.nniett.kikaishin.app.persistence.entity.listener.ReviewEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "review")
@EntityListeners({AuditingEntityListener.class, ReviewEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
public class ReviewEntity extends ImmutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "user_fk", columnDefinition = "VARCHAR(16)")
    private String userId;

    @ManyToMany
    @JoinTable(
            name = "question_review_grade",
            joinColumns = @JoinColumn(name = "review_fk"),
            inverseJoinColumns = @JoinColumn(name = "question_fk")
    )
    private List<QuestionEntity> questions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "review_user_fk"),
            name = "user_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "review_fk")
    private List<QuestionReviewGradeEntity> questionReviewGrades;

}

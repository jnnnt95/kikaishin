package com.nniett.kikaishin.web.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.web.persistence.entity.construction.ImmutableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
public class ReviewEntity extends ImmutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, name = "user_fk", columnDefinition = "VARCHAR(16)")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "review_user_fk"),
            name = "user_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private UserEntity user;

    @OneToMany(mappedBy = "reviewId", fetch = FetchType.LAZY)
    private List<QuestionReviewGradeEntity> questionReviewGrades;

}

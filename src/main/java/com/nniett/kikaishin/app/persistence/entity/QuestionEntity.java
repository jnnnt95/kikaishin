package com.nniett.kikaishin.app.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.persistence.entity.construction.ActivateableTextMutableEntity;
import com.nniett.kikaishin.app.persistence.entity.listener.QuestionEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "question")
@EntityListeners({AuditingEntityListener.class, QuestionEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
public class QuestionEntity extends ActivateableTextMutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "topic_fk")
    private Integer topicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "question_topic_fk"),
            name = "topic_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private TopicEntity topic;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "question_fk")
    private List<AnswerEntity> answers;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "question_fk")
    private List<ClueEntity> clues;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "question_fk")
    @JsonIgnore
    private List<QuestionReviewGradeEntity> questionReviewGrades;

    @OneToOne(mappedBy = "question", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "question_fk")
    @JsonIgnore
    private ReviewModelEntity reviewModel;

    @ManyToMany(mappedBy = "questions", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ReviewEntity> reviews;

}

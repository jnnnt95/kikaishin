package com.nniett.kikaishin.web.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.web.persistence.entity.construction.ActivateableTextMutableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
public class QuestionEntity extends ActivateableTextMutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, name = "topic_fk")
    private Integer topicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "question_topic_fk"),
            name = "topic_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private TopicEntity topic;

    @OneToMany(mappedBy = "questionId", fetch = FetchType.LAZY)
    private List<AnswerEntity> answers;

    @OneToMany(mappedBy = "questionId", fetch = FetchType.LAZY)
    private List<ClueEntity> clues;

    @OneToMany(mappedBy = "questionId", fetch = FetchType.LAZY)
    private List<QuestionReviewGradeEntity> questionReviewGrades;

}

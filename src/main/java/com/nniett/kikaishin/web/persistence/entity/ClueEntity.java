package com.nniett.kikaishin.web.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.web.persistence.entity.construction.TextMutableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clue")
@Getter
@Setter
@NoArgsConstructor
public class ClueEntity extends TextMutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, name = "question_fk")
    private Integer questionId;

    @Column(name = "order_index")
    private Integer orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "clue_question_fk"),
            name = "question_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private QuestionEntity question;

}
package com.nniett.kikaishin.web.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.web.persistence.entity.construction.DescribableActivateableMutableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "topic")
@Getter
@Setter
@NoArgsConstructor
public class TopicEntity extends DescribableActivateableMutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, name = "book_fk")
    private Integer bookId;

    @Column(nullable = false, name = "lookup_key", columnDefinition = "VARCHAR(30)")
    private String lookupKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "topic_book_fk"),
            name = "book_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private BookEntity book;

    @OneToMany(mappedBy = "topicId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<QuestionEntity> questions;

}

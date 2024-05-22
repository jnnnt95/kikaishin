package com.nniett.kikaishin.app.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.persistence.entity.construction.DescribableActivateableMutableEntity;
import com.nniett.kikaishin.app.persistence.entity.listener.TopicEntityListener;
import com.nniett.kikaishin.common.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "topic")
@EntityListeners({AuditingEntityListener.class, TopicEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TopicEntity extends DescribableActivateableMutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "book_fk")
    private Integer bookId;

    @Column(name = "lookup_key", columnDefinition = Constants.LOOKUP_KEY_COLUMN_DEFINITION)
    private String lookupKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "topic_book_fk"),
            name = "book_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private BookEntity book;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "topic_fk")
    private List<QuestionEntity> questions;

}

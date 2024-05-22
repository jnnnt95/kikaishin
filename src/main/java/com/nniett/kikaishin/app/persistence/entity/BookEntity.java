package com.nniett.kikaishin.app.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.persistence.entity.construction.DescribableActivateableMutableEntity;
import com.nniett.kikaishin.app.persistence.entity.listener.BookEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "book")
@EntityListeners({AuditingEntityListener.class, BookEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookEntity extends DescribableActivateableMutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "shelf_fk")
    private Integer shelfId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "book_shelf_fk"),
            name = "shelf_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private ShelfEntity shelf;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "book_fk")
    private List<TopicEntity> topics;

}

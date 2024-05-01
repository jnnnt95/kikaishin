package com.nniett.kikaishin.web.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.web.persistence.entity.construction.DescribableActivateableMutableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "book")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class BookEntity extends DescribableActivateableMutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, name = "shelf_fk")
    private Integer shelfId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "book_shelf_fk"),
            name = "shelf_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private ShelfEntity shelf;

    @OneToMany(mappedBy = "bookId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TopicEntity> topics;

}

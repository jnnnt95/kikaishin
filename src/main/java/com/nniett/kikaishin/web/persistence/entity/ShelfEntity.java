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
@Table(name = "shelf")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class ShelfEntity  extends DescribableActivateableMutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, name = "user_fk", columnDefinition = "VARCHAR(16)")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "shelf_user_fk"),
            name = "user_fk",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private UserEntity user;

    @OneToMany(mappedBy = "shelfId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BookEntity> books;

}

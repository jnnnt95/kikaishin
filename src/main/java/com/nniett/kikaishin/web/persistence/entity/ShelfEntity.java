package com.nniett.kikaishin.web.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.web.persistence.entity.sup.auditing.ActivableMutableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shelf")
@Getter
@Setter
@NoArgsConstructor
public class ShelfEntity  extends ActivableMutableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, name = "user_fk", columnDefinition = "VARCHAR(16)")
    private String username;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(columnDefinition = "VARCHAR(250)")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "user_fk"),
            name = "username",
            insertable = false,
            updatable = false)
    @JsonIgnore
    private UserEntity user;

}

package com.nniett.kikaishin.app.persistence.entity;

import com.nniett.kikaishin.app.persistence.entity.construction.MutableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends MutableEntity {

    @Id
    @Column(nullable = false, columnDefinition = "VARCHAR(16)")
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "display_name", nullable = false, columnDefinition = "VARCHAR(25)")
    private String displayName;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(25)")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_fk", referencedColumnName = "username")
    private List<ShelfEntity> shelves;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;

}

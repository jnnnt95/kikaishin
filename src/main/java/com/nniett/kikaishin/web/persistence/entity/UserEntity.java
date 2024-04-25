package com.nniett.kikaishin.web.persistence.entity;

import com.nniett.kikaishin.web.persistence.entity.construction.MutableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(mappedBy = "username", fetch = FetchType.LAZY)
    private List<ShelfEntity> shelves;

}

package com.nniett.kikaishin.app.persistence.entity;

import com.nniett.kikaishin.app.persistence.entity.construction.MutableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import static com.nniett.kikaishin.common.Constants.*;

@Entity
@Table(name = "user")
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserEntity extends MutableEntity {

    @Id
    @Column(nullable = false, columnDefinition = USERNAME_COLUMN_DEFINITION)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "display_name", nullable = false, columnDefinition = DISPLAY_NAME_COLUMN_DEFINITION)
    private String displayName;

    @Column(unique = true, nullable = false, columnDefinition = EMAIL_COLUMN_DEFINITION)
    private String email;

    @Column(nullable = false, columnDefinition = TINYINT_DB_DEF)
    private Boolean locked;

    @Column(nullable = false, columnDefinition = TINYINT_DB_DEF)
    private Boolean disabled;

    @Column(nullable = false, name = "failed_authentications")
    private Integer failedAuthentications;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_fk", referencedColumnName = "username")
    private List<ShelfEntity> shelves;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
    List<UserRoleEntity> roles;

}

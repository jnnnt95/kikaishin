package com.nniett.kikaishin.app.persistence.entity;

import com.nniett.kikaishin.app.persistence.entity.construction.MutableEntity;
import com.nniett.kikaishin.app.persistence.entity.id.UserRoleEntityPK;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_role")
@IdClass(UserRoleEntityPK.class)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class UserRoleEntity extends MutableEntity {
    @Id
    @Column(nullable = false, columnDefinition = "VARCHAR(16)")
    private String username;

    @Id
    @Column(nullable = false, columnDefinition = "VARCHAR(16)")
    private String role;

    @Column(name = "granted_date")
    private LocalDateTime grantedDate;

    @ManyToOne
    @JoinColumn(name = "username",
            referencedColumnName = "username",
            insertable = false, updatable = false)
    private UserEntity user;

    @Override
    public String toString() {
        return this.role;
    }
}

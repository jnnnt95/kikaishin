package com.nniett.kikaishin.web.persistence.entity.sup.auditing;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class MutableEntity {

    @Column(name = "create_date", nullable = false)
    @CreatedDate
    LocalDateTime creationDate;

    @Column(name = "update_date")
    @LastModifiedDate
    LocalDateTime updateDate;

}

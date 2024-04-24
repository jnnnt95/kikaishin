package com.nniett.kikaishin.web.persistence.entity.sup.auditing;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class ImmutableEntity {

    @Column(name = "create_date", nullable = false)
    @CreatedDate
    LocalDateTime creationDate;

}

package com.nniett.kikaishin.web.persistence.entity.sup.auditing;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@MappedSuperclass
@Getter
@Setter
public abstract class ActivableMutableEntity extends MutableEntity {

    @Column(name = "active", nullable = false, columnDefinition = "TINYINT")
    @CreatedDate
    Boolean active;

}

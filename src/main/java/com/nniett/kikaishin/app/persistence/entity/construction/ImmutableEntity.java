package com.nniett.kikaishin.app.persistence.entity.construction;

import com.nniett.kikaishin.common.Constants;
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

    @Column(name = Constants.CREATION_DATE_COLUMN_NAME, nullable = false)
    @CreatedDate
    private LocalDateTime creationDate;

}

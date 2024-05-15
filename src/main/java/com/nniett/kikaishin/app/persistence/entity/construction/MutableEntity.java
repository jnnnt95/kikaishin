package com.nniett.kikaishin.app.persistence.entity.construction;

import com.nniett.kikaishin.common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class MutableEntity extends ImmutableEntity {

    @Column(name = Constants.UPDATE_DATE_COLUMN_NAME)
    @LastModifiedDate
    private LocalDateTime updateDate;

}

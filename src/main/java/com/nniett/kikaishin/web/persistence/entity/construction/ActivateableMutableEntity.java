package com.nniett.kikaishin.web.persistence.entity.construction;

import com.nniett.kikaishin.common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class ActivateableMutableEntity extends MutableEntity {

    @Column(nullable = false, columnDefinition = Constants.ACTIVE_COLUMN_DEFINITION)
    private Boolean active;

}

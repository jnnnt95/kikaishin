package com.nniett.kikaishin.web.persistence.entity.construction;

import com.nniett.kikaishin.web.persistence.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class DescribableActivateableMutableEntity extends ActivateableMutableEntity {

    @Column(nullable = false, columnDefinition = Constants.NAME_COLUMN_DEFINITION)
    private String name;

    @Column(columnDefinition = Constants.DESCRIPTION_COLUMN_DEFINITION)
    private String description;
}
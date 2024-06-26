package com.nniett.kikaishin.app.persistence.entity.construction;

import com.nniett.kikaishin.common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class TextMutableEntity extends MutableEntity {

    @Column(nullable = false, columnDefinition = Constants.TEXT_BODY_COLUMN_DEFINITION)
    private String body;

}

package com.nniett.kikaishin.web.persistence.entity.construction;

import com.nniett.kikaishin.web.persistence.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class ActivateableTextMutableEntity extends ActivateableMutableEntity {

    @Column(nullable = false, columnDefinition = Constants.TEXT_BODY_COLUMN_DEFINITION)
    private String body;

}
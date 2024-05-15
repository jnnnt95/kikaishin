package com.nniett.kikaishin.app.service.pojo.dto;

public interface ActivateableUpdateDto<PK> extends UpdateDto<PK> {
    Boolean getActive();
}

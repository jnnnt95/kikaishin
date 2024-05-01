package com.nniett.kikaishin.web.service.pojo.dto;

public interface ActivateableUpdateDto<PK> extends UpdateDto<PK> {
    Boolean getActive();
}

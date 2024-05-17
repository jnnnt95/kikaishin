package com.nniett.kikaishin.app.service.dto.write;

public interface ActivateableUpdateDto<PK> extends UpdateDto<PK> {
    Boolean getActive();
}

package com.nniett.kikaishin.app.service.dto.write;

import com.nniett.kikaishin.app.service.dto.common.Pojo;

public interface CreationDto<PK> extends Pojo<PK> {
    @Override
    PK getPK();
}

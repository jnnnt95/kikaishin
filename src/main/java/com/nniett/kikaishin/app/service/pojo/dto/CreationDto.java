package com.nniett.kikaishin.app.service.pojo.dto;

import com.nniett.kikaishin.app.service.pojo.Pojo;
import com.nniett.kikaishin.app.service.pojo.common.HasParent;

public interface CreationDto<PK> extends Pojo<PK> {
    @Override
    PK getPK();
}

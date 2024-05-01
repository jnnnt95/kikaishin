package com.nniett.kikaishin.web.service.pojo.dto;

import com.nniett.kikaishin.web.service.pojo.Pojo;

public interface CreationDto<PK> extends Pojo<PK> {
    @Override
    PK getPK();
}

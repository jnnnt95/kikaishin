package com.nniett.kikaishin.app.service.pojo.dto;

import com.nniett.kikaishin.app.service.pojo.Pojo;

public interface UpdateDto<PK> extends Pojo<PK> {
    @Override
    PK getPK();
}

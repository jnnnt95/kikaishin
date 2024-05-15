package com.nniett.kikaishin.app.service.pojo.dto;

import com.nniett.kikaishin.app.service.pojo.common.HasParent;

public interface CreationDtoWithParent<PARENT_PK> extends HasParent<PARENT_PK> {
    @Override
    PARENT_PK getParentPK();
    @Override
    void setParentPK(PARENT_PK parentPK);
}

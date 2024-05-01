package com.nniett.kikaishin.web.service.pojo.dto;

import com.nniett.kikaishin.web.service.pojo.common.HasParent;

public interface CreationDtoWithParent<PARENT_PK> extends HasParent<PARENT_PK> {
    @Override
    PARENT_PK getParentPK();
    @Override
    void setParentPK(PARENT_PK parentPK);
}

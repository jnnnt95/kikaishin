package com.nniett.kikaishin.app.service.dto.write;

import com.nniett.kikaishin.app.service.dto.common.HasParent;

public interface CreationDtoWithParent<PARENT_PK> extends HasParent<PARENT_PK> {
    @Override
    PARENT_PK getParentPK();
    @Override
    void setParentPK(PARENT_PK parentPK);
}

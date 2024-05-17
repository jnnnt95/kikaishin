package com.nniett.kikaishin.app.service.dto.common;

public interface HasParent<PARENT_PK> {
    PARENT_PK getParentPK();
    void setParentPK(PARENT_PK parentPK);
}

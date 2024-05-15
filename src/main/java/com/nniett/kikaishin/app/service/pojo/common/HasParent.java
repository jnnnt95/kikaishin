package com.nniett.kikaishin.app.service.pojo.common;

public interface HasParent<PARENT_PK> {
    PARENT_PK getParentPK();
    void setParentPK(PARENT_PK parentPK);
}

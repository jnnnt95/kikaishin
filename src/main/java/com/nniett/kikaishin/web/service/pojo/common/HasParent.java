package com.nniett.kikaishin.web.service.pojo.common;

public interface HasParent<PARENT_PK> {
    PARENT_PK getParentPK();
    void setParentPK(PARENT_PK parentPK);
}

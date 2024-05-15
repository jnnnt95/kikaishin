package com.nniett.kikaishin.app.service.pojo.common;

import java.util.List;

public interface HasChildren<CHILD_TYPE> {
    List<CHILD_TYPE> getChildren();
    void setChildren(List<CHILD_TYPE> children);
}

package com.nniett.kikaishin.web.service.pojo.common;

import com.nniett.kikaishin.web.service.pojo.Pojo;

import java.util.List;

public interface HasChildren<CHILD_TYPE> {
    List<CHILD_TYPE> getChildren();
    void setChildren(List<CHILD_TYPE> children);
}

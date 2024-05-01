package com.nniett.kikaishin.web.service.pojo.dto;

import com.nniett.kikaishin.web.service.pojo.common.HasChildren;

import java.util.List;

public interface CreationDtoWithChildren<CHILD_CREATION_DTO> extends HasChildren<CHILD_CREATION_DTO> {
    @Override
    List<CHILD_CREATION_DTO> getChildren();
    @Override
    void setChildren(List<CHILD_CREATION_DTO> children);
}

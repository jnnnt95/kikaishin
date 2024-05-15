package com.nniett.kikaishin.app.service.pojo.dto;

import com.nniett.kikaishin.app.service.pojo.common.HasChildren;

import java.util.List;

public interface CreationDtoWithChildren<PK, CHILD_CREATION_DTO> extends HasChildren<CHILD_CREATION_DTO>, CreationDto<PK> {
    @Override
    List<CHILD_CREATION_DTO> getChildren();
    @Override
    void setChildren(List<CHILD_CREATION_DTO> children);
}

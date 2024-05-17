package com.nniett.kikaishin.app.service.dto.write;

import com.nniett.kikaishin.app.service.dto.common.HasChildren;

import java.util.List;

public interface CreationDtoWithChildren<PK, CHILD_CREATION_DTO> extends HasChildren<CHILD_CREATION_DTO>, CreationDto<PK> {
    @Override
    List<CHILD_CREATION_DTO> getChildren();
    @Override
    void setChildren(List<CHILD_CREATION_DTO> children);
}

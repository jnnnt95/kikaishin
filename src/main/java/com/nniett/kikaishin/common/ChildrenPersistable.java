package com.nniett.kikaishin.common;

public interface ChildrenPersistable<CHILD_POJO, CHILD_CREATION_DTO> {
    CHILD_POJO createChild(CHILD_CREATION_DTO child);
}

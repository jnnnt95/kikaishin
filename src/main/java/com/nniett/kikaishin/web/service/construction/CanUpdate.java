package com.nniett.kikaishin.web.service.construction;

public interface CanUpdate<ENTITY, UPDATE_DTO, POJO> {

    POJO update(POJO pojo);

    void populateEntityForUpdate(ENTITY entity, POJO dto);

    POJO updateFromDto(UPDATE_DTO dto);

}

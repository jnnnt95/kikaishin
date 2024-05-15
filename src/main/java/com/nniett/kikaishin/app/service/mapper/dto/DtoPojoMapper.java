package com.nniett.kikaishin.app.service.mapper.dto;

public interface DtoPojoMapper<DTO, POJO> {
    POJO toPojo(DTO dto);
    DTO toDto(POJO pojo);
}

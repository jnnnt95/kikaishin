package com.nniett.kikaishin.web.service.mapper.dto;

import org.springframework.stereotype.Component;

public interface DtoPojoMapper<DTO, POJO> {
    POJO toPojo(DTO dto);
    DTO toDto(POJO pojo);
}

package com.nniett.kikaishin.app.service.mapper;

import java.util.List;

public interface EntityPojoMapper <ENTITY, POJO> {
    POJO toPojo(ENTITY entity);
    List<POJO> toPojos(List<ENTITY> entities);
    ENTITY toEntity(POJO pojo);
    List<ENTITY> toEntities(List<POJO> pojos);
}

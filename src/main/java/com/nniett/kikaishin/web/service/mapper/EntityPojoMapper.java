package com.nniett.kikaishin.web.service.mapper;

import java.util.List;

public interface EntityPojoMapper <ENTITY, POJO> {
    public POJO toPojo(ENTITY entity);
    public List<POJO> toPojos(List<ENTITY> entities);
    public ENTITY toEntity(POJO pojo);
    public List<ENTITY> toEntities(List<POJO> pojos);
}

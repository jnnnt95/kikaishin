package com.nniett.kikaishin.web.service.construction;

import java.util.List;

public interface CanRead<ENTITY, UPDATE_DTO, PK, POJO> {

    boolean exists(PK id);

    public POJO read(PK id);

    public List<POJO> readAll();

    //This method needs to retrieve entity from db and modify the relevant fields of such entity from update dto.
    public abstract ENTITY findEntityByDto(UPDATE_DTO dto);

}
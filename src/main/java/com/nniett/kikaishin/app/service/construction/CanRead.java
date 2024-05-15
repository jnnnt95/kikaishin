package com.nniett.kikaishin.app.service.construction;

import java.util.List;

public interface CanRead<ENTITY, PK, POJO> {

    boolean exists(PK id);

    POJO readPojo(PK id);

    ENTITY readEntity(PK id);

    List<POJO> readAll();

}

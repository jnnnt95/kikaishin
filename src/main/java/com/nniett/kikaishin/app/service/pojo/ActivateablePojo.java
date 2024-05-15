package com.nniett.kikaishin.app.service.pojo;

import com.nniett.kikaishin.app.service.pojo.common.Activateable;

public interface ActivateablePojo<PK> extends Pojo<PK>, Activateable {
    @Override
    PK getPK();
    @Override
    Boolean getActive();
    @Override
    void setActive(Boolean active);
}

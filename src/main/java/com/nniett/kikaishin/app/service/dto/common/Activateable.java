package com.nniett.kikaishin.app.service.dto.common;

import java.util.List;

public interface Activateable {
    Boolean getActive();
    void setActive(Boolean active);

    default void initForCreate() {
        this.setActive(true);
        if(this instanceof HasChildren<?>) {
            List<?> children = ((HasChildren<?>) this).getChildren();
            if(children != null) {
                for(var child: children) {
                    if(child instanceof Activateable) {
                        ((Activateable) child).setActive(true);
                    }
                    if(child instanceof Activateable) {
                        ((Activateable) child).initForCreate();
                    }
                }
            }
        }
    }
}

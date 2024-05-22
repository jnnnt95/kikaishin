package com.nniett.kikaishin.app.service.dto.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface Activateable {
    Logger logger = LoggerFactory.getLogger(Activateable.class);

    Boolean getActive();
    void setActive(Boolean active);

    default void initForCreate() {
        logger.debug("Initializing activateable object.");
        this.setActive(true);
        if(this instanceof HasChildren<?>) {
            List<?> children = ((HasChildren<?>) this).getChildren();
            if(children != null) {
                logger.debug("Initializing object's activateable children.");
                for(var child: children) {
                    if(child instanceof Activateable) {
                        ((Activateable) child).initForCreate();
                    }
                }
            }
        }
    }
}

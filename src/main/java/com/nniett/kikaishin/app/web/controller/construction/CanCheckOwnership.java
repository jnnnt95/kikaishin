package com.nniett.kikaishin.app.web.controller.construction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface CanCheckOwnership<PK> {
    Logger logger = LoggerFactory.getLogger(CanCheckOwnership.class);
    //if at least one is not own, this should return false
    boolean own(List<PK> pks);
    default boolean own(PK pk) {
        logger.debug("Validating ownership of object.");
        logger.trace("Object under ownership validation: {}.", pk);
        return own(List.of(pk));
    }
}

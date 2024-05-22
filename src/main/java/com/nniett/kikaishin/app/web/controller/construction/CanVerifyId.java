package com.nniett.kikaishin.app.web.controller.construction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public interface CanVerifyId<PK> {
    Logger logger = LoggerFactory.getLogger(CanVerifyId.class);
    //if at least one is not own, this should return false
    boolean valid(Collection<PK> pks);
    default boolean valid(PK pk) {
        logger.debug("Validating pk value.");
        logger.trace("Pk value under validation: {}.", pk);
        return valid(List.of(pk));
    }
}

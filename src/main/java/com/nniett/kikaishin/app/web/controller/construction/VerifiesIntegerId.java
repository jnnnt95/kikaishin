package com.nniett.kikaishin.app.web.controller.construction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public interface VerifiesIntegerId {
    Logger logger = LoggerFactory.getLogger(VerifiesIntegerId.class);
    default boolean validIntegers(Collection<Integer> ids) {
        logger.debug("Validating integer values.");
        logger.trace("Integer values under validation: {}.", ids);
        if(ids != null && !ids.isEmpty()) {
            for(Integer id : ids) {
                if(id == null || id < 1) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}

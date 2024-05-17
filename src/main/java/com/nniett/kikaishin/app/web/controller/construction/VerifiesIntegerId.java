package com.nniett.kikaishin.app.web.controller.construction;

import java.util.Collection;

public interface VerifiesIntegerId {
    default boolean validIntegers(Collection<Integer> ids) {
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

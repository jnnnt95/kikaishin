package com.nniett.kikaishin.app.web.controller.construction;

import java.util.Collection;
import java.util.List;

public interface CanVerifyId<PK> {
    //if at least one is not own, this should return false
    boolean valid(Collection<PK> pks);
    default boolean valid(PK pk) {
        return valid(List.of(pk));
    }
}

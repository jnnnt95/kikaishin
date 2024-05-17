package com.nniett.kikaishin.app.web.controller.construction;

import java.util.List;

public interface CanCheckOwnership<PK> {
    //if at least one is not own, this should return false
    boolean own(List<PK> pks);
    default boolean own(PK pk) {
        return own(List.of(pk));
    }
}

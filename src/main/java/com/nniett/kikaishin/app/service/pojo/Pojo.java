package com.nniett.kikaishin.app.service.pojo;

import com.nniett.kikaishin.app.service.pojo.common.HasChildren;
import com.nniett.kikaishin.app.service.pojo.common.HasParent;

import java.util.List;

public interface Pojo<PK> {
    PK getPK();

    default void afterCreate() {
        if(this instanceof HasChildren<?>) {
            List<?> children = ((HasChildren<?>) this).getChildren();
            if(children != null) {
                for(var child: children) {
                    if(child instanceof HasParent<?>) {
                        ((HasParent<PK>) child).setParentPK(this.getPK());
                    }
                }
            }
        }
    }
}

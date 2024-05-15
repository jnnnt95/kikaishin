package com.nniett.kikaishin.app.service.pojo;

import com.nniett.kikaishin.app.service.pojo.common.Activateable;
import com.nniett.kikaishin.app.service.pojo.common.HasChildren;
import com.nniett.kikaishin.app.service.pojo.common.HasParent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class User implements HasChildren<Shelf>, Pojo<String> {
    private String username;
    private String displayName;
    private String email;
    private List<Shelf> shelves;

    @Override
    public String getPK() {
        return "";
    }

    @Override
    public List<Shelf> getChildren() {
        return List.of();
    }

    @Override
    public void setChildren(List<Shelf> children) {

    }
}

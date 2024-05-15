package com.nniett.kikaishin.app.service.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nniett.kikaishin.app.service.pojo.common.Activateable;
import com.nniett.kikaishin.app.service.pojo.common.HasChildren;
import com.nniett.kikaishin.app.service.pojo.common.HasParent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Book implements HasParent<Integer>, HasChildren<Topic>, Pojo<Integer>, Activateable {
    private Integer bookId;
    @JsonIgnore
    private Integer shelfId;
    private String name;
    private String description;
    private Boolean active;
    @JsonIgnore
    private LocalDateTime creationDate;
    @JsonIgnore
    private LocalDateTime updateDate;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<Topic> topics;
    @JsonIgnore
    private Shelf parentShelf;

    @Override
    @JsonIgnore
    public Integer getPK() {
        return bookId;
    }

    @Override
    @JsonIgnore
    public List<Topic> getChildren() {
        return topics;
    }

    @Override
    public void setChildren(List<Topic> children) {
        this.topics = children;
    }

    @Override
    @JsonIgnore
    public Integer getParentPK() {
        return this.shelfId;
    }

    @Override
    public void setParentPK(Integer parentPK) {
        this.shelfId = parentPK;
    }
}

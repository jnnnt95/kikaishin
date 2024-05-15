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
public class Topic
        implements HasParent<Integer>,
        HasChildren<Question>,
        Pojo<Integer>,
        Activateable {
    private Integer topicId;
    @JsonIgnore
    private Integer bookId;
    private String name;
    private String description;
    private String lookupKey;
    private Boolean active;
    @JsonIgnore
    private LocalDateTime createDate;
    @JsonIgnore
    private LocalDateTime updateDate;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<Question> questions;
    @JsonIgnore
    private Book parentBook;

    @Override
    @JsonIgnore
    public Integer getParentPK() {
        return bookId;
    }

    @Override
    public void setParentPK(Integer parentPK) {
        this.bookId = parentPK;
    }

    @Override
    @JsonIgnore
    public List<Question> getChildren() {
        return this.questions;
    }

    @Override
    public void setChildren(List<Question> children) {
        this.questions = children;
    }

    @Override
    @JsonIgnore
    public Integer getPK() {
        return this.topicId;
    }
}

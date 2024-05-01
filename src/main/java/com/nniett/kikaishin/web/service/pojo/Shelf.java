package com.nniett.kikaishin.web.service.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nniett.kikaishin.web.service.pojo.common.HasChildren;
import com.nniett.kikaishin.web.service.pojo.common.HasParent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Shelf implements HasParent<String>, HasChildren<Book>, Pojo<Integer> {
    private Integer shelfId;
    @JsonIgnore
    private String username;
    private String name;
    private String description;
    private Boolean active;
    @JsonIgnore
    private LocalDateTime creationDate;
    @JsonIgnore
    private LocalDateTime updateDate;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<Book> books;
    @JsonIgnore
    private User user;

    @Override
    public Integer getPK() {
        return shelfId;
    }

    @Override
    public List<Book> getChildren() {
        return books;
    }

    @Override
    public void setChildren(List<Book> children) {
        this.books = children;
    }

    @Override
    public String getParentPK() {
        return username;
    }

    @Override
    public void setParentPK(String parentPK) {
        this.username = parentPK;
    }
}

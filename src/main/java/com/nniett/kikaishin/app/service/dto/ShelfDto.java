package com.nniett.kikaishin.app.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nniett.kikaishin.app.service.dto.common.Activateable;
import com.nniett.kikaishin.app.service.dto.common.HasChildren;
import com.nniett.kikaishin.app.service.dto.common.HasParent;
import com.nniett.kikaishin.app.service.dto.common.Pojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ShelfDto implements HasParent<String>, HasChildren<BookDto>, Pojo<Integer>, Activateable {
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
    private List<BookDto> books;
    @JsonIgnore
    private UserDto user;

    @Override
    @JsonIgnore
    public Integer getPK() {
        return shelfId;
    }

    @Override
    @JsonIgnore
    public List<BookDto> getChildren() {
        return books;
    }

    @Override
    public void setChildren(List<BookDto> children) {
        this.books = children;
    }

    @Override
    @JsonIgnore
    public String getParentPK() {
        return username;
    }

    @Override
    public void setParentPK(String parentPK) {
        this.username = parentPK;
    }


}

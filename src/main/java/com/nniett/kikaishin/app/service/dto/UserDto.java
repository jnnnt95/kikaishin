package com.nniett.kikaishin.app.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nniett.kikaishin.app.service.dto.common.HasChildren;
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
public class UserDto implements HasChildren<ShelfDto>, Pojo<String> {
    private String username;
    @JsonIgnore
    private String password;
    private String displayName;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ShelfDto> shelves;
    @JsonIgnore
    private LocalDateTime createDate;
    @JsonIgnore
    private LocalDateTime updateDate;

    @Override
    @JsonIgnore
    public String getPK() {
        return "";
    }

    @Override
    @JsonIgnore
    public List<ShelfDto> getChildren() {
        return shelves;
    }

    @Override
    public void setChildren(List<ShelfDto> children) {
        this.shelves = children;
    }
}

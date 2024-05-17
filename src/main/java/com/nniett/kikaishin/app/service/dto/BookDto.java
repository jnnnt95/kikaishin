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

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookDto implements HasParent<Integer>, HasChildren<TopicDto>, Pojo<Integer>, Activateable {
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
    private List<TopicDto> topics;
    @JsonIgnore
    private ShelfDto parentShelf;

    @Override
    @JsonIgnore
    public Integer getPK() {
        return bookId;
    }

    @Override
    @JsonIgnore
    public List<TopicDto> getChildren() {
        return topics;
    }

    @Override
    public void setChildren(List<TopicDto> children) {
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

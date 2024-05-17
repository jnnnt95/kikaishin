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
public class TopicDto
        implements HasParent<Integer>,
        HasChildren<QuestionDto>,
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
    private List<QuestionDto> questions;
    @JsonIgnore
    private BookDto parentBook;

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
    public List<QuestionDto> getChildren() {
        return this.questions;
    }

    @Override
    public void setChildren(List<QuestionDto> children) {
        this.questions = children;
    }

    @Override
    @JsonIgnore
    public Integer getPK() {
        return this.topicId;
    }
}

package com.nniett.kikaishin.web.service.pojo;

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
public class Topic implements HasParent<Integer>, HasChildren<Question> {
    private Integer topicId;
    private Integer bookId;
    private String name;
    private String description;
    private String lookupKey;
    private Boolean active;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<Question> questions;
    private Book parentBook;

    @Override
    public Integer getParentPK() {
        return bookId;
    }

    @Override
    public void setParentPK(Integer parentPK) {
        this.bookId = parentPK;
    }

    @Override
    public List<Question> getChildren() {
        return this.questions;
    }

    @Override
    public void setChildren(List<Question> children) {
        this.questions = children;
    }
}

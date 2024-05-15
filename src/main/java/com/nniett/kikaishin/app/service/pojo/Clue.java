package com.nniett.kikaishin.app.service.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.pojo.common.HasParent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Clue implements HasParent<Integer>, Pojo<Integer> {
    private Integer clueId;
    @JsonIgnore
    private Integer questionId;
    private Integer orderIndex;
    private String body;
    @JsonIgnore
    private LocalDateTime createDate;
    @JsonIgnore
    private LocalDateTime updateDate;
    @JsonIgnore
    private Question question;

    @Override
    @JsonIgnore
    public Integer getParentPK() {
        return this.questionId;
    }

    @Override
    public void setParentPK(Integer parentPK) {
        this.questionId = parentPK;
    }

    @JsonIgnore
    @Override
    public Integer getPK() {
        return this.clueId;
    }
}

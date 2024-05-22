package com.nniett.kikaishin.app.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.common.HasParent;
import com.nniett.kikaishin.app.service.dto.common.Pojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClueDto implements HasParent<Integer>, Pojo<Integer> {
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
    private QuestionDto question;

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

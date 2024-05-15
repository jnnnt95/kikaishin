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
public class ReviewModel implements HasParent<Integer> {
    @JsonIgnore
    private Integer reviewModelId;
    @JsonIgnore
    private Integer questionId;
    private Float x0;
    private Float x1;
    private Float x2;
    private Integer expectedReviews;
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
}

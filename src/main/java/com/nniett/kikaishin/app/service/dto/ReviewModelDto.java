package com.nniett.kikaishin.app.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.common.HasParent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewModelDto implements HasParent<Integer> {
    @JsonIgnore
    private Integer reviewModelId;
    @JsonIgnore
    private Integer questionId;
    private Float x0;
    private Float x1;
    private Float x2;
    private Integer expectedReviews;
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
}

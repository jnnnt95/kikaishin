package com.nniett.kikaishin.app.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ReviewDto implements HasParent<String>, Pojo<Integer> {
    private Integer reviewId;
    private List<QuestionReviewGradeDto> questionGrades;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private LocalDateTime createDate;
    @JsonIgnore
    private UserDto user;
    @JsonIgnore
    private List<QuestionDto> questions;

    @Override
    @JsonIgnore
    public Integer getPK() {
        return this.reviewId;
    }

    @Override
    @JsonIgnore
    public String getParentPK() {
        return this.username;
    }

    @Override
    public void setParentPK(String parentPK) {
        this.username = parentPK;
    }
}

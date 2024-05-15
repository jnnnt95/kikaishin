package com.nniett.kikaishin.app.service.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.pojo.common.HasParent;
import com.nniett.kikaishin.app.service.pojo.dto.questionreviewgrade.QuestionReviewGradeCreationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Review implements HasParent<String>, Pojo<Integer> {
    private Integer reviewId;
    private List<QuestionReviewGrade> questionGrades;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private LocalDateTime createDate;
    @JsonIgnore
    private User user;
    @JsonIgnore
    private List<Question> questions;

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

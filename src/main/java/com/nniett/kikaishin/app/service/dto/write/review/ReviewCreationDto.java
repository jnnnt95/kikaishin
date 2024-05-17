package com.nniett.kikaishin.app.service.dto.write.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.write.CreationDto;
import com.nniett.kikaishin.app.service.dto.write.CreationDtoWithParent;
import com.nniett.kikaishin.app.service.dto.write.questionreviewgrade.QuestionReviewGradeCreationDto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ReviewCreationDto implements
        CreationDtoWithParent<String>, CreationDto<Integer> {

    private Integer reviewId;

    private String username;

    @NotEmpty(message = "Creation of a review record requires at least one review grade.")
    private List<QuestionReviewGradeCreationDto> questionGrades;

    @JsonIgnore
    @Override
    public String getParentPK() {
        return this.username;
    }

    @JsonIgnore
    @Override
    public void setParentPK(String parentPK) {
        this.username = parentPK;
    }

    @JsonIgnore
    @Override
    public Integer getPK() {
        return this.reviewId;
    }
}

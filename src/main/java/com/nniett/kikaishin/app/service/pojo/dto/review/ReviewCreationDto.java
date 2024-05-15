package com.nniett.kikaishin.app.service.pojo.dto.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDtoWithParent;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.questionreviewgrade.QuestionReviewGradeCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.reviewmodel.ReviewModelCreationDto;
import com.nniett.kikaishin.common.Constants;
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

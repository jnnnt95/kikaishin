package com.nniett.kikaishin.app.service.dto.write.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.write.CreationDto;
import com.nniett.kikaishin.app.service.dto.write.CreationDtoWithParent;
import com.nniett.kikaishin.app.service.dto.write.answer.AnswerCreationDto;
import com.nniett.kikaishin.app.service.dto.write.clue.ClueCreationDto;
import com.nniett.kikaishin.app.service.dto.write.reviewmodel.ReviewModelCreationDto;
import com.nniett.kikaishin.common.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuestionCreationDto implements
        CreationDtoWithParent<Integer>,
        CreationDto<Integer> {

    @JsonIgnore
    private Integer questionId;

    private int topicId;

    @NotBlank(message = "Body cannot be empty.")
    @Size(max = Constants.TEXT_BODY_SIZE,
            message = "Body cannot have more than " + Constants.TEXT_BODY_SIZE + " characters.")
    private String body;

    @Valid @NotNull(message = "Question cannot be created without review model.")
    private ReviewModelCreationDto reviewModel;

    @Valid @NotEmpty(message = "Question cannot be created without at least one answer.")
    private List<AnswerCreationDto> answers;

    @Valid
    private List<ClueCreationDto> clues;

    @JsonIgnore
    @Override
    public Integer getParentPK() {
        return topicId;
    }

    @JsonIgnore
    @Override
    public void setParentPK(Integer parentPK) {
        this.topicId = parentPK;
    }

    @JsonIgnore
    @Override
    public Integer getPK() {
        return this.questionId;
    }
}

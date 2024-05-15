package com.nniett.kikaishin.app.service.pojo.dto.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDtoWithParent;
import com.nniett.kikaishin.app.service.pojo.dto.answer.AnswerCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.reviewmodel.ReviewModelCreationDto;
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

    private Integer questionId;

    //moving validation to controller due to validation incompatibility for creation as child object.
//    @Min(value = 1, message = "Provided Topic id not valid.")
//    @NotNull(message = "Topic id must be provided.")
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

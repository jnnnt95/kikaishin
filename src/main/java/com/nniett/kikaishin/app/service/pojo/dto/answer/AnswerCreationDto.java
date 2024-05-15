package com.nniett.kikaishin.app.service.pojo.dto.answer;

import com.nniett.kikaishin.app.service.pojo.dto.CreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDtoWithParent;
import com.nniett.kikaishin.app.service.pojo.dto.clue.ClueCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.reviewmodel.ReviewModelCreationDto;
import com.nniett.kikaishin.common.Constants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AnswerCreationDto implements
        CreationDtoWithParent<Integer>,
        CreationDto<Integer> {

    private Integer answerId;
    private Integer questionId;

    @NotBlank(message = "Body cannot be empty.")
    @Size(max = Constants.TEXT_BODY_SIZE,
            message = "Body cannot have more than " + Constants.TEXT_BODY_SIZE + " characters.")
    private String body;

    @NotNull(message = "Order index must be provided.")
    @Min(value = 1, message = "Order index must be positive.")
    private Integer orderIndex;

    @Override
    public Integer getParentPK() {
        return questionId;
    }

    @Override
    public void setParentPK(Integer parentPK) {
        this.questionId = parentPK;
    }

    @Override
    public Integer getPK() {
        return this.answerId;
    }
}

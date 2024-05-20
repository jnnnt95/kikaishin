package com.nniett.kikaishin.app.service.dto.write.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.write.UpdateDto;
import com.nniett.kikaishin.common.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AnswerUpdateDto implements UpdateDto<Integer> {

    @Min(value = 1, message = "Id not valid.")
    @NotNull(message = "Answer Id must be provided.")
    private Integer answerId;

    @NotBlank(message = "Body cannot be empty.")
    @Size(max = Constants.TEXT_BODY_SIZE,
            message = "Body cannot have more than " + Constants.TEXT_BODY_SIZE + " characters.")
    private String body;

    @NotNull(message = "Order index must be provided.")
    @Min(value = 1, message = "Order index must be positive.")
    private Integer orderIndex;

    @JsonIgnore
    @Override
    public Integer getPK() {
        return answerId;
    }
}

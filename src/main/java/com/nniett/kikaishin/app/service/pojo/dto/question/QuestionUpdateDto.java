package com.nniett.kikaishin.app.service.pojo.dto.question;

import com.nniett.kikaishin.app.service.pojo.dto.ActivateableUpdateDto;
import com.nniett.kikaishin.common.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuestionUpdateDto implements ActivateableUpdateDto<Integer> {

    @Min(value = 1, message = "Id not valid.")
    @NotNull(message = "Question Id must be provided.")
    private Integer questionId;

    private String body;

    private Boolean active;

    @Override
    public Integer getPK() {
        return questionId;
    }
}

package com.nniett.kikaishin.app.service.dto.write.question;

import com.nniett.kikaishin.app.service.dto.write.ActivateableUpdateDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

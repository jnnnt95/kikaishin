package com.nniett.kikaishin.app.service.dto.write.answer;

import com.nniett.kikaishin.app.service.dto.write.UpdateDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnswerUpdateDto implements UpdateDto<Integer> {

    @Min(value = 1, message = "Id not valid.")
    @NotNull(message = "Answer Id must be provided.")
    private Integer answerId;

    private Integer orderIndex;

    private String body;

    @Override
    public Integer getPK() {
        return answerId;
    }
}

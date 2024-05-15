package com.nniett.kikaishin.app.service.pojo.dto.clue;

import com.nniett.kikaishin.app.service.pojo.dto.UpdateDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClueUpdateDto implements UpdateDto<Integer> {

    @Min(value = 1, message = "Id not valid.")
    @NotNull(message = "Clue Id must be provided.")
    private Integer clueId;

    private Integer orderIndex;

    private String body;

    @Override
    public Integer getPK() {
        return clueId;
    }
}

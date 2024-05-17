package com.nniett.kikaishin.app.service.dto.write.clue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.write.UpdateDto;
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

    @JsonIgnore
    @Override
    public Integer getPK() {
        return clueId;
    }
}

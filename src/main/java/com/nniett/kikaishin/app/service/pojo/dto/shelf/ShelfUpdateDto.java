package com.nniett.kikaishin.app.service.pojo.dto.shelf;

import com.nniett.kikaishin.common.Constants;
import com.nniett.kikaishin.app.service.pojo.dto.ActivateableUpdateDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShelfUpdateDto implements ActivateableUpdateDto<Integer> {

    @Min(value = 1, message = "Id not valid.")
    @NotNull(message = "Shelf Id must be provided.")
    private Integer shelfId;

    private String name;

    private String description;

    private Boolean active;

    @Override
    public Integer getPK() {
        return shelfId;
    }
}

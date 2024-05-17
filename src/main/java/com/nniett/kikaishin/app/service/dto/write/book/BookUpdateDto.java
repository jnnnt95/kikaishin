package com.nniett.kikaishin.app.service.dto.write.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.write.ActivateableUpdateDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookUpdateDto implements ActivateableUpdateDto<Integer> {

    @Min(value = 1, message = "Id not valid.")
    @NotNull(message = "Book Id must be provided.")
    private Integer bookId;

    private String name;

    private String description;

    private Boolean active;

    @JsonIgnore
    @Override
    public Integer getPK() {
        return bookId;
    }
}

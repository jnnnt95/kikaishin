package com.nniett.kikaishin.app.service.dto.write.reviewmodel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewModelCreationDto {
    @Min(value = 1, message = "Provided Question id not valid.")
    private Integer questionId;

    @NotNull(message = "Value of x0 of new Review Model must be provided.")
    @Min(value = 1, message = "Value of x0 must be greater or equal to 1.")
    private Float x0;

    @Min(value = 0, message = "Value of x0 must be greater or equal to 0.")
    private Float x1;

    @Min(value = 0, message = "Value of x0 must be greater or equal to 0.")
    private Float x2;

    @Min(value = 1, message = "Expected reviews must be greater than 0.")
    private Integer expectedReviews;
}

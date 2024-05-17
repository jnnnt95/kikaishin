package com.nniett.kikaishin.app.service.dto.write.questionreviewgrade;

import com.nniett.kikaishin.app.service.dto.write.CreationDto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionReviewGradeCreationDto implements CreationDto<Integer> {

    @Min(value = 1, message = "Provided Question id not valid.")
    @NotNull(message = "Question Id must be provided.")
    private int questionId;
    @Min(value = 1, message = "Provided Review id not valid.")
    @NotNull(message = "Review Id must be provided.")
    private int reviewId;

    @NotNull(message = "Provided grade value is not valid.")
    @Min(value = 0, message = "Provided grade value cannot be lower than 0.")
    @Max(value = 5, message = "Provided grade value cannot be greater than 5.")
    private Integer gradeValue;

    @Override
    public Integer getPK() {
        return reviewId;
    }
}

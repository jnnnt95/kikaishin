package com.nniett.kikaishin.app.service.dto.write.clue;

import com.nniett.kikaishin.app.service.dto.write.CreationDto;
import com.nniett.kikaishin.app.service.dto.write.CreationDtoWithParent;
import com.nniett.kikaishin.common.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClueCreationDto implements
        CreationDtoWithParent<Integer>,
        CreationDto<Integer> {

    private Integer clueId;

    private Integer questionId;

    @NotBlank(message = "Body cannot be empty.")
    @Size(max = Constants.TEXT_BODY_SIZE,
            message = "Body cannot have more than " + Constants.TEXT_BODY_SIZE + " characters.")
    private String body;

    @NotNull(message = "Order index must be provided.")
    @Min(value = 1, message = "Order index must be positive.")
    private Integer orderIndex;

    @Override
    public Integer getParentPK() {
        return questionId;
    }

    @Override
    public void setParentPK(Integer parentPK) {
        this.questionId = parentPK;
    }

    @Override
    public Integer getPK() {
        return this.clueId;
    }
}
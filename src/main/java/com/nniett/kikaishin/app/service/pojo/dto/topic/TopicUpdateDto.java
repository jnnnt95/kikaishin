package com.nniett.kikaishin.app.service.pojo.dto.topic;

import com.nniett.kikaishin.common.Constants;
import com.nniett.kikaishin.app.service.pojo.dto.ActivateableUpdateDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TopicUpdateDto implements ActivateableUpdateDto<Integer> {

    @Min(value = 1, message = "Id not valid.")
    @NotNull(message = "Topic id must be provided.")
    private Integer topicId;

    private String name;

    private String description;

    private Boolean active;

    @Override
    public Integer getPK() {
        return topicId;
    }
}

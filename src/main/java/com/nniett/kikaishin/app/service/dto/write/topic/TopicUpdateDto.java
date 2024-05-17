package com.nniett.kikaishin.app.service.dto.write.topic;

import com.nniett.kikaishin.app.service.dto.write.ActivateableUpdateDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

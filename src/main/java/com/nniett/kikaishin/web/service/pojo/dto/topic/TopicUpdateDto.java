package com.nniett.kikaishin.web.service.pojo.dto.topic;

import com.nniett.kikaishin.common.Constants;
import com.nniett.kikaishin.web.service.pojo.dto.ActivateableUpdateDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TopicUpdateDto implements ActivateableUpdateDto<Integer> {

    @Min(value = 1, message = "Id not valid.")
    private Integer topicId;

    @Size(max = Constants.NAME_SIZE,
            message = "Name cannot have more than " + Constants.NAME_SIZE + " characters.")
    private String name;

    @Size(max = Constants.DESCRIPTION_BODY_SIZE,
            message = "Description cannot have more than " + Constants.DESCRIPTION_BODY_SIZE + " characters.")
    private String description;

    private Boolean active;

    @Override
    public Integer getPK() {
        return topicId;
    }
}

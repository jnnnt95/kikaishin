package com.nniett.kikaishin.web.service.pojo.dto.topic;

import com.nniett.kikaishin.common.Constants;
import com.nniett.kikaishin.web.service.pojo.dto.CreationDtoWithParent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Setter;

import java.util.List;

@Setter
public class TopicCreationDto implements CreationDtoWithParent<Integer> {

    @Min(value = 1, message = "Provided Shelf id not valid.")
    private int bookId;

    @NotBlank(message = "Name cannot be empty.")
    @Size(max = Constants.NAME_SIZE,
            message = "Name cannot have more than " + Constants.NAME_SIZE + " characters.")
    private String name;

    @Size(max = Constants.DESCRIPTION_BODY_SIZE,
            message = "Description cannot have more than " + Constants.DESCRIPTION_BODY_SIZE + " characters.")
    private String description;
    private String lookupKey;
    private List<TopicCreationDto> questions;

    @Override
    public Integer getParentPK() {
        return bookId;
    }

    @Override
    public void setParentPK(Integer parentPK) {
        this.bookId = parentPK;
    }
}

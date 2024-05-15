package com.nniett.kikaishin.app.service.pojo.dto.book;

import com.nniett.kikaishin.common.Constants;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDtoWithChildren;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDtoWithParent;
import com.nniett.kikaishin.app.service.pojo.dto.topic.TopicCreationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BookCreationDto implements CreationDtoWithChildren<Integer, TopicCreationDto>, CreationDtoWithParent<Integer> {

    private Integer bookId;

    private Integer shelfId;

    @NotBlank(message = "Name cannot be empty.")
    @Size(max = Constants.NAME_SIZE,
            message = "Name cannot have more than " + Constants.NAME_SIZE + " characters.")
    private String name;

    @Size(max = Constants.DESCRIPTION_BODY_SIZE,
            message = "Description cannot have more than " + Constants.DESCRIPTION_BODY_SIZE + " characters.")
    private String description;

    @Valid
    private List<TopicCreationDto> topics;

    @Override
    public List<TopicCreationDto> getChildren() {
        return topics;
    }

    @Override
    public void setChildren(List<TopicCreationDto> children) {
        this.topics = children;
    }

    @Override
    public Integer getParentPK() {
        return this.shelfId;
    }

    @Override
    public void setParentPK(Integer parentPK) {
        this.shelfId = parentPK;
    }

    @Override
    public Integer getPK() {
        return this.bookId;
    }
}

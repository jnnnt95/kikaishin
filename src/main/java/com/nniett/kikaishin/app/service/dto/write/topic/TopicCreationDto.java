package com.nniett.kikaishin.app.service.dto.write.topic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.write.CreationDtoWithChildren;
import com.nniett.kikaishin.app.service.dto.write.question.QuestionCreationDto;
import com.nniett.kikaishin.common.Constants;
import com.nniett.kikaishin.app.service.dto.write.CreationDtoWithParent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TopicCreationDto implements
        CreationDtoWithParent<Integer>,
        CreationDtoWithChildren<Integer, QuestionCreationDto>
{

    @JsonIgnore
    private Integer topicId;

    private int bookId;

    @NotBlank(message = "Name cannot be empty.")
    @Size(max = Constants.OBJECT_NAME_SIZE,
            message = "Name cannot have more than " + Constants.OBJECT_NAME_SIZE + " characters.")
    private String name;

    @Size(max = Constants.DESCRIPTION_BODY_SIZE,
            message = "Description cannot have more than " + Constants.DESCRIPTION_BODY_SIZE + " characters.")
    private String description;
    private String lookupKey;

    @Valid
    private List<QuestionCreationDto> questions;

    @JsonIgnore
    @Override
    public Integer getParentPK() {
        return bookId;
    }

    @JsonIgnore
    @Override
    public void setParentPK(Integer parentPK) {
        this.bookId = parentPK;
    }

    @JsonIgnore
    @Override
    public List<QuestionCreationDto> getChildren() {
        return this.questions;
    }

    @JsonIgnore
    @Override
    public void setChildren(List<QuestionCreationDto> children) {
        this.questions = children;
    }

    @JsonIgnore
    @Override
    public Integer getPK() {
        return this.topicId;
    }
}

package com.nniett.kikaishin.app.service.pojo.dto.topic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDtoWithChildren;
import com.nniett.kikaishin.app.service.pojo.dto.question.QuestionCreationDto;
import com.nniett.kikaishin.common.Constants;
import com.nniett.kikaishin.app.service.pojo.dto.CreationDtoWithParent;
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

    private Integer topicId;

    //@Min(value = 1, message = "Provided Book id not valid.")
    //@NotNull(message = "Book id must be provided.")
    private int bookId;

    @NotBlank(message = "Name cannot be empty.")
    @Size(max = Constants.NAME_SIZE,
            message = "Name cannot have more than " + Constants.NAME_SIZE + " characters.")
    private String name;

    @Size(max = Constants.DESCRIPTION_BODY_SIZE,
            message = "Description cannot have more than " + Constants.DESCRIPTION_BODY_SIZE + " characters.")
    private String description;
    private String lookupKey;

    @Valid
    private List<QuestionCreationDto> questions;

    @Override
    public Integer getParentPK() {
        return bookId;
    }

    @Override
    public void setParentPK(Integer parentPK) {
        this.bookId = parentPK;
    }
    @JsonIgnore
    @Override
    public List<QuestionCreationDto> getChildren() {
        return this.questions;
    }

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
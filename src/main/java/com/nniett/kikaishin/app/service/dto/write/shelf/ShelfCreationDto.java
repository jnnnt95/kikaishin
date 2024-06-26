package com.nniett.kikaishin.app.service.dto.write.shelf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.common.Constants;
import com.nniett.kikaishin.app.service.dto.write.CreationDtoWithChildren;
import com.nniett.kikaishin.app.service.dto.write.book.BookCreationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ShelfCreationDto implements CreationDtoWithChildren<Integer, BookCreationDto> {

    @JsonIgnore
    private Integer shelfId;

    @NotBlank(message = "Name cannot be empty.")
    @Size(max = Constants.OBJECT_NAME_SIZE,
            message = "Name cannot have more than " + Constants.OBJECT_NAME_SIZE + " characters.")
    private String name;

    @Size(max = Constants.DESCRIPTION_BODY_SIZE,
            message = "Description cannot have more than " + Constants.DESCRIPTION_BODY_SIZE + " characters.")
    private String description;

    @Valid
    private List<BookCreationDto> books;

    @JsonIgnore
    @Override
    public List<BookCreationDto> getChildren() {
        return books;
    }

    @JsonIgnore
    @Override
    public void setChildren(List<BookCreationDto> children) {
        this.books = children;
    }

    @JsonIgnore
    @Override
    public Integer getPK() {
        return this.shelfId;
    }
}

package com.nniett.kikaishin.web.service.pojo.dto.shelf;

import com.nniett.kikaishin.common.Constants;
import com.nniett.kikaishin.web.service.pojo.dto.CreationDtoWithChildren;
import com.nniett.kikaishin.web.service.pojo.dto.book.BookCreationDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ShelfCreationDto implements CreationDtoWithChildren<BookCreationDto> {

    @NotBlank(message = "Name cannot be empty.")
    @Size(max = Constants.NAME_SIZE,
            message = "Name cannot have more than " + Constants.NAME_SIZE + " characters.")
    private String name;

    @Size(max = Constants.DESCRIPTION_BODY_SIZE,
            message = "Description cannot have more than " + Constants.DESCRIPTION_BODY_SIZE + " characters.")
    private String description;

    private List<BookCreationDto> books;

    @Override
    public List<BookCreationDto> getChildren() {
        return books;
    }

    @Override
    public void setChildren(List<BookCreationDto> children) {
        this.books = children;
    }
}

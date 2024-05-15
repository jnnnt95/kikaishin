package com.nniett.kikaishin.app.service.pojo.dto.user;

import com.nniett.kikaishin.app.service.pojo.dto.CreationDtoWithChildren;
import com.nniett.kikaishin.app.service.pojo.dto.book.BookCreationDto;
import com.nniett.kikaishin.app.service.pojo.dto.shelf.ShelfCreationDto;
import com.nniett.kikaishin.common.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserCreationDto implements CreationDtoWithChildren<String, ShelfCreationDto> {

    @Override
    public List<ShelfCreationDto> getChildren() {
        return List.of();
    }

    @Override
    public void setChildren(List<ShelfCreationDto> children) {

    }

    @Override
    public String getPK() {
        return "";
    }
}

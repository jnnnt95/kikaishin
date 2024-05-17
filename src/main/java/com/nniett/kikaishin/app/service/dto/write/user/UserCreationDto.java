package com.nniett.kikaishin.app.service.dto.write.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.write.CreationDtoWithChildren;
import com.nniett.kikaishin.app.service.dto.write.shelf.ShelfCreationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

import static com.nniett.kikaishin.common.Constants.*;

@Data
public class UserCreationDto implements CreationDtoWithChildren<String, ShelfCreationDto> {

    @NotBlank(message = "Username must be provided.")
    @NotNull(message = "Username must be provided.")
    @Pattern(regexp = USERNAME_REGEX)
    private String username;

    @NotBlank(message = "Password must be provided.")
    @NotNull(message = "Password must be provided.")
    @Pattern(regexp = PASSWORD_REGEX)
    private String password;

    @NotBlank(message = "Display Name must be provided.")
    @NotNull(message = "Display Name must be provided.")
    @Length(min = 3, max = 25)
    private String displayName;

    @NotBlank(message = "E-Mail must be provided.")
    @NotNull(message = "E-Mail must be provided.")
    @Length(min = 6, max = 25)
    @Pattern(regexp = EMAIL_REGEX)
    private String email;

    @Valid
    private List<ShelfCreationDto> shelves;

    @Override
    @JsonIgnore
    public List<ShelfCreationDto> getChildren() {
        return this.shelves;
    }

    @Override
    public void setChildren(List<ShelfCreationDto> children) {
        this.shelves = children;
    }

    @Override
    @JsonIgnore
    public String getPK() {
        return username;
    }

}

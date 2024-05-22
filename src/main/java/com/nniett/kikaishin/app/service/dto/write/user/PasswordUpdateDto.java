package com.nniett.kikaishin.app.service.dto.write.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static com.nniett.kikaishin.common.Constants.PASSWORD_REGEX;

@Getter
@Setter
public class PasswordUpdateDto {

    @NotBlank(message = "Old password must be provided.")
    @NotNull(message = "Old password must be provided.")
    private String oldPassword;

    @NotNull(message = "New password must be provided.")
    @NotBlank(message = "New password must be provided.")
    @Length(min = 12, max = 50, message = "Minimum password length is 12 characters.")
    @Pattern(regexp = PASSWORD_REGEX)
    private String newPassword;

    @Override
    public String toString() {
        return "PasswordUpdateDto{" +
                "oldPassword='" + "***" + '\'' +
                ", newPassword='" + "***" + '\'' +
                '}';
    }
}

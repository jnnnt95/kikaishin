package com.nniett.kikaishin.app.service.pojo.dto.user;

import com.nniett.kikaishin.app.service.pojo.dto.ActivateableUpdateDto;
import com.nniett.kikaishin.common.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDto implements ActivateableUpdateDto<String> {

    @Override
    public Boolean getActive() {
        return null;
    }

    @Override
    public String getPK() {
        return "";
    }
}

package com.nniett.kikaishin.app.service.pojo.dto.user;

import com.nniett.kikaishin.app.service.pojo.dto.ActivateableUpdateDto;
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

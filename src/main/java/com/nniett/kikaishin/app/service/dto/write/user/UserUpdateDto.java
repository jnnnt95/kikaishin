package com.nniett.kikaishin.app.service.dto.write.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.dto.write.UpdateDto;
import lombok.Data;

@Data
public class UserUpdateDto implements UpdateDto<String> {

    private String username;
    private String displayName;
    private String email;

    @JsonIgnore
    @Override
    public String getPK() {
        return username;
    }
}

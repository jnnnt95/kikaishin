package com.nniett.kikaishin.web.service.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private String username;
    private String displayName;
    private String email;
    private List<Shelf> shelves;
}

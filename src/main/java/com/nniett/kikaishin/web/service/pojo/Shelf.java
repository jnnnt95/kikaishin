package com.nniett.kikaishin.web.service.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Shelf {
    private int id;
    private String name;
    private String description;
    private boolean active;
    private User user;
}

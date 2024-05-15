package com.nniett.kikaishin.app.service.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewableAnswer {
    private Integer answerId;
    private Integer orderIndex;
    private String body;
}

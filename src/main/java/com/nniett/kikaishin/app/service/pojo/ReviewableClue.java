package com.nniett.kikaishin.app.service.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewableClue {
    private Integer clueId;
    private Integer orderIndex;
    private String body;
}
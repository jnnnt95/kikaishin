package com.nniett.kikaishin.app.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewableAnswerDto {
    private Integer answerId;
    private Integer orderIndex;
    private String body;
}

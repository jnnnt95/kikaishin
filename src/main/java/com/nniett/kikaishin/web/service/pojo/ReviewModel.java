package com.nniett.kikaishin.web.service.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewModel {
    private Integer reviewModelId;
    private Integer questionId;
    private Float x0;
    private Float x1;
    private Float x2;
    private Integer expectedReviews;
    private Question question;
}

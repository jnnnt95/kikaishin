package com.nniett.kikaishin.app.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewableQuestionDto {
    private Integer questionId;
    private String body;
    private Integer orderIndex;
    private String lookupKey;
    private LocalDateTime creationDate;
    private LocalDateTime nextReviewDate;
    private List<ReviewableAnswerDto> answers;
    private List<ReviewableClueDto> clues;
}

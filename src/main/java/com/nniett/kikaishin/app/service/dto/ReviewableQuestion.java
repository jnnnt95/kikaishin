package com.nniett.kikaishin.app.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewableQuestion {
    private Integer questionId;
    private String body;
    private Integer orderIndex;
    private String lookupKey;
    private LocalDateTime creationDate;
    private LocalDateTime nextReviewDate;
    private List<ReviewableAnswer> answers;
    private List<ReviewableClue> clues;
}

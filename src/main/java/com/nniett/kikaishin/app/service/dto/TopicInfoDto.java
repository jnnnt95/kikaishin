package com.nniett.kikaishin.app.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class TopicInfoDto {
    private Integer topicId;
    private Boolean active;
    private Long age;
    private Float gradeAverage;
    private Integer totalQuestions;
    private Integer totalQuestionsToReview;
    private LocalDateTime createDate;
    private List<Integer> questionIds;
}

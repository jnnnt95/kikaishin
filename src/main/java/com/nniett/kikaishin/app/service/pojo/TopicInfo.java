package com.nniett.kikaishin.app.service.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TopicInfo {
    private Integer topicId;
    private Boolean active;
    private Long age;
    private Float gradeAverage;
    private Integer totalQuestions;
    private Integer totalQuestionsToReview;
    private LocalDateTime createDate;
    private List<Integer> questionIds;
}

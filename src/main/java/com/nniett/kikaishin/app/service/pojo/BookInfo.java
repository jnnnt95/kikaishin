package com.nniett.kikaishin.app.service.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BookInfo {
    private Integer bookId;
    private Boolean active;
    private Long age;
    private Float gradeAverage;
    private Integer totalTopics;
    private Integer totalQuestions;
    private Integer totalQuestionsToReview;
    private LocalDateTime createDate;
    private List<Integer> topicIds;
}

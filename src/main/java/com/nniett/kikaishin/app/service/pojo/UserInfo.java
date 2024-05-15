package com.nniett.kikaishin.app.service.pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserInfo {
    private String username;
    private String displayName;
    private String email;
    private Float gradeAverage;
    private Integer totalShelves;
    private Integer totalBooks;
    private Integer totalTopics;
    private Integer totalQuestions;
    private Integer totalQuestionsToReview;
    private LocalDateTime createDate;
    private List<Integer> shelfIds;
}

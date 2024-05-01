package com.nniett.kikaishin.web.service.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Question {
    private Integer questionId;
    private Integer topicId;
    private String body;
    private Boolean active;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private ReviewModel reviewModel;
    private List<Answer> answers;
    private List<Clue> clues;
    private List<QuestionReviewGrade> reviewGrades;
    private Book parentBook;
}

package com.nniett.kikaishin.app.persistence.entity.virtual;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShelfInfoVirtualEntity {
    private Integer shelfId;
    private Boolean active;
    private Long age; // calculated
    private Float gradeAverage; // calculated
    private Integer totalBooks; // calculated
    private Integer totalTopics; // calculated
    private Long totalQuestions;// calculated
    private Long totalQuestionsToReview; // calculated
    private LocalDateTime createDate;
    private List<BookInfoVirtualEntity> booksInfo;
    private List<Integer> bookIds;

    public void calculate() {
        updateAge();
        updateGradeAverage();
        updateTotalBooks();
        updateTotalTopics();
        updateTotalQuestions();
        updateTotalQuestionsToReview();
        updateBookIds();
    }

    private void updateTotalBooks() {
        this.totalBooks = this.booksInfo.size();
    }

    private void updateTotalTopics() {
        this.totalTopics = this.booksInfo.stream().mapToInt(BookInfoVirtualEntity::getTotalTopics).sum();
    }

    private void updateBookIds() {
        this.bookIds = new ArrayList<>();
        booksInfo.forEach(q -> bookIds.add(q.getBookId()));
    }

    private void updateAge() {
        this.age = ChronoUnit.DAYS.between(createDate, LocalDateTime.now());
    }

    private void updateGradeAverage() {
        this.gradeAverage =
                (float) this.booksInfo.stream().
                        mapToDouble(q -> {
                            if(q.getGradeAverage() == null) return 0;
                            else return q.getGradeAverage();
                        }).average().orElse(0);
    }

    private void updateTotalQuestions() {
        this.totalQuestions = this.booksInfo.stream().mapToLong(BookInfoVirtualEntity::getTotalQuestions).sum();
    }

    private void updateTotalQuestionsToReview() {
        this.totalQuestionsToReview =
                this.booksInfo.stream()
                        .flatMap(b -> b.getTopicsInfo().stream())
                        .flatMap(t -> t.getQuestionsInfo().stream())
                        .filter(QuestionInfoVirtualEntity::getIsToReview).count();
    }


}

package com.nniett.kikaishin.app.persistence.entity.virtual;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserInfoVirtualEntity {
    private String username;
    private String displayName;
    private String email;
    private Float gradeAverage; // calculated
    private Integer totalShelves; // calculated
    private Integer totalBooks; // calculated
    private Integer totalTopics; // calculated
    private Long totalQuestions;// calculated
    private Long totalQuestionsToReview; // calculated
    private LocalDateTime createDate;
    private List<ShelfInfoVirtualEntity> shelvesInfo;
    private List<Integer> shelfIds;

    public void calculate() {
        updateGradeAverage();
        updateTotalShelves();
        updateTotalBooks();
        updateTotalTopics();
        updateTotalQuestions();
        updateTotalQuestionsToReview();
        updateShelfIds();
    }

    public void updateTotalShelves() {
        this.totalShelves = this.shelvesInfo.size();
    }

    private void updateTotalBooks() {
        this.totalBooks = this.shelvesInfo.stream().mapToInt(ShelfInfoVirtualEntity::getTotalBooks).sum();
    }

    private void updateTotalTopics() {
        this.totalTopics = this.shelvesInfo.stream().flatMap(s -> s.getBooksInfo().stream())
                .mapToInt(BookInfoVirtualEntity::getTotalTopics).sum();
    }

    private void updateShelfIds() {
        this.shelfIds = new ArrayList<>();
        shelvesInfo.forEach(q -> shelfIds.add(q.getShelfId()));
    }

    private void updateGradeAverage() {
        this.gradeAverage =
                (float) this.shelvesInfo.stream().
                        mapToDouble(q -> {
                            if(q.getGradeAverage() == null) return 0;
                            else return q.getGradeAverage();
                        }).average().getAsDouble();
    }

    private void updateTotalQuestions() {
        this.totalQuestions = this.shelvesInfo.stream().mapToLong(ShelfInfoVirtualEntity::getTotalQuestions).sum();
    }

    private void updateTotalQuestionsToReview() {
        this.totalQuestionsToReview =
                this.shelvesInfo.stream()
                        .flatMap(s -> s.getBooksInfo().stream())
                        .flatMap(b -> b.getTopicsInfo().stream())
                        .flatMap(t -> t.getQuestionsInfo().stream())
                        .filter(QuestionInfoVirtualEntity::getIsToReview).count();
    }


}

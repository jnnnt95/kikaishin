package com.nniett.kikaishin.app.persistence.entity.virtual;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ShelfInfoVirtualEntity {

    private static final Logger logger = LoggerFactory.getLogger(ShelfInfoVirtualEntity.class);

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
        logger.debug("Executing ShelfInfoVirtualEntity calculate action.");
        logger.debug("Calculate action being executed for ShelfInfoVirtualEntity representation of shelf id {}.", shelfId);
        updateAge();
        updateGradeAverage();
        updateTotalBooks();
        updateTotalTopics();
        updateTotalQuestions();
        updateTotalQuestionsToReview();
        updateBookIds();
    }

    private void updateTotalBooks() {
        logger.debug("Updating total books.");
        this.totalBooks = this.booksInfo.size();
    }

    private void updateTotalTopics() {
        logger.debug("Updating total topics.");
        this.totalTopics = this.booksInfo.stream().mapToInt(BookInfoVirtualEntity::getTotalTopics).sum();
    }

    private void updateBookIds() {
        logger.debug("Updating book ids.");
        this.bookIds = new ArrayList<>();
        booksInfo.forEach(q -> bookIds.add(q.getBookId()));
    }

    private void updateAge() {
        logger.debug("Updating age.");
        this.age = ChronoUnit.DAYS.between(createDate, LocalDateTime.now());
    }

    private void updateGradeAverage() {
        logger.debug("Updating grade average.");
        this.gradeAverage =
                (float) this.booksInfo.stream().
                        mapToDouble(q -> {
                            if(q.getGradeAverage() == null) return 0;
                            else return q.getGradeAverage();
                        }).average().orElse(0);
    }

    private void updateTotalQuestions() {
        logger.debug("Updating total questions.");
        this.totalQuestions = this.booksInfo.stream().mapToLong(BookInfoVirtualEntity::getTotalQuestions).sum();
    }

    private void updateTotalQuestionsToReview() {
        logger.debug("Updating total questions to review.");
        this.totalQuestionsToReview =
                this.booksInfo.stream()
                        .flatMap(b -> b.getTopicsInfo().stream())
                        .flatMap(t -> t.getQuestionsInfo().stream())
                        .filter(QuestionInfoVirtualEntity::getIsToReview).count();
    }


}

package com.nniett.kikaishin.app.persistence.entity.virtual;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class UserInfoVirtualEntity {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoVirtualEntity.class);

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
        logger.debug("Executing UserInfoVirtualEntity calculate action.");
        logger.debug("Calculate action being executed for UserInfoVirtualEntity representation of username {}.", username);
        updateGradeAverage();
        updateTotalShelves();
        updateTotalBooks();
        updateTotalTopics();
        updateTotalQuestions();
        updateTotalQuestionsToReview();
        updateShelfIds();
    }

    public void updateTotalShelves() {
        logger.debug("Updating total shelves.");
        this.totalShelves = this.shelvesInfo.size();
    }

    private void updateTotalBooks() {
        logger.debug("Updating total books.");
        this.totalBooks = this.shelvesInfo.stream().mapToInt(ShelfInfoVirtualEntity::getTotalBooks).sum();
    }

    private void updateTotalTopics() {
        logger.debug("Updating total topics.");
        this.totalTopics = this.shelvesInfo.stream().flatMap(s -> s.getBooksInfo().stream())
                .mapToInt(BookInfoVirtualEntity::getTotalTopics).sum();
    }

    private void updateShelfIds() {
        logger.debug("Updating shelf ids.");
        this.shelfIds = new ArrayList<>();
        shelvesInfo.forEach(q -> shelfIds.add(q.getShelfId()));
    }

    private void updateGradeAverage() {
        logger.debug("Updating grade average.");
        this.gradeAverage =
                (float) this.shelvesInfo.stream().
                        mapToDouble(q -> {
                            if(q.getGradeAverage() == null) return 0;
                            else return q.getGradeAverage();
                        }).average().orElse(0);
    }

    private void updateTotalQuestions() {
        logger.debug("Updating total questions.");
        this.totalQuestions = this.shelvesInfo.stream().mapToLong(ShelfInfoVirtualEntity::getTotalQuestions).sum();
    }

    private void updateTotalQuestionsToReview() {
        logger.debug("Updating total questions to review.");
        this.totalQuestionsToReview =
                this.shelvesInfo.stream()
                        .flatMap(s -> s.getBooksInfo().stream())
                        .flatMap(b -> b.getTopicsInfo().stream())
                        .flatMap(t -> t.getQuestionsInfo().stream())
                        .filter(QuestionInfoVirtualEntity::getIsToReview).count();
    }


}

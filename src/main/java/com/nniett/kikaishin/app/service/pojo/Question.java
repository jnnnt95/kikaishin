package com.nniett.kikaishin.app.service.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nniett.kikaishin.app.service.pojo.common.Activateable;
import com.nniett.kikaishin.app.service.pojo.common.HasChildren;
import com.nniett.kikaishin.app.service.pojo.common.HasParent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Question implements HasParent<Integer>, HasChildren<HasParent<Integer>>, Pojo<Integer>, Activateable {
    private Integer questionId;
    @JsonIgnore
    private Integer topicId;
    private String body;
    private Boolean active;
    @JsonIgnore
    private LocalDateTime createDate;
    @JsonIgnore
    private LocalDateTime updateDate;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private ReviewModel reviewModel;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<Answer> answers;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<Clue> clues;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<QuestionReviewGrade> reviewGrades;
    @JsonIgnore
    private Topic topic;

    @Override
    @JsonIgnore
    public Integer getPK() {
        return questionId;
    }

    @Override
    @JsonIgnore
    public Integer getParentPK() {
        return topicId;
    }

    @Override
    public void setParentPK(Integer parentPK) {
        this.topicId = parentPK;
    }

    @Override
    public List<HasParent<Integer>> getChildren() {
        List<HasParent<Integer>> hasParents = new ArrayList<>();

        hasParents.addAll(this.answers != null ? this.answers : List.of());
        hasParents.addAll(this.clues != null ? this.clues : List.of());
        if(reviewModel != null) hasParents.add(reviewModel);

        return hasParents;
    }

    @Override
    @JsonIgnore
    public void setChildren(List<HasParent<Integer>> children) {
        if(children == null) {
            this.answers = null;
            this.clues = null;
            this.reviewModel = null;
            this.reviewGrades = null;
        } else {
            for(HasParent<Integer> child : children) {
                if(child instanceof Answer) this.answers.add((Answer) child);
                if(child instanceof Clue) this.clues.add((Clue) child);
                if(child instanceof ReviewModel) this.reviewModel = (ReviewModel) child;
                if(child instanceof QuestionReviewGrade) this.reviewGrades.add((QuestionReviewGrade) child);
            }
        }
    }

    @Override
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public Boolean getActive() {
        return this.active;
    }
}

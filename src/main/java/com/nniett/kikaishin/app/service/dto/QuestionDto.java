package com.nniett.kikaishin.app.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nniett.kikaishin.app.service.dto.common.Activateable;
import com.nniett.kikaishin.app.service.dto.common.HasChildren;
import com.nniett.kikaishin.app.service.dto.common.HasParent;
import com.nniett.kikaishin.app.service.dto.common.Pojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDto implements HasParent<Integer>, HasChildren<HasParent<Integer>>, Pojo<Integer>, Activateable {
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
    private ReviewModelDto reviewModel;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<AnswerDto> answers;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<ClueDto> clues;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<QuestionReviewGradeDto> reviewGrades;
    @JsonIgnore
    private TopicDto topic;

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
                if(child instanceof AnswerDto) this.answers.add((AnswerDto) child);
                if(child instanceof ClueDto) this.clues.add((ClueDto) child);
                if(child instanceof ReviewModelDto) this.reviewModel = (ReviewModelDto) child;
                if(child instanceof QuestionReviewGradeDto) this.reviewGrades.add((QuestionReviewGradeDto) child);
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

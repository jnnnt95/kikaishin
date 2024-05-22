package com.nniett.kikaishin.app.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.forget.risk.RiskFactorsClassifiers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecommendedQuestionDto {
    private Integer questionId;
    private String reason;
    @JsonIgnore
    private RiskFactorsClassifiers.Classifier classifier;
}

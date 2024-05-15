package com.nniett.kikaishin.app.service.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.forget.risk.RiskFactorsClassifiers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecommendedQuestion {
    private Integer questionId;
    private String reason;
    @JsonIgnore
    private RiskFactorsClassifiers.Classifier classifier;
}

package com.nniett.kikaishin.app.service.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nniett.kikaishin.app.service.forget.risk.RiskFactorsClassifiers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecommendedQuestion {
    private Integer questionId;
    private String reason;
    @JsonIgnore
    private RiskFactorsClassifiers.Classifier classifier;
}

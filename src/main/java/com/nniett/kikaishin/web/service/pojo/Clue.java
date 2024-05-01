package com.nniett.kikaishin.web.service.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Clue {
    private Integer clueId;
    private Integer questionId;
    private Integer orderIndex;
    private String body;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Question parentQuestion;
}

package org.batikan.test.system.dto;

import lombok.Data;

@Data
public class AnswerResultDto {
    private int questionNumber;
    private Character studentChoice;
    private Character rightChoice;
    private boolean correctlyAnswered;
}

package org.batikan.test.system.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestResultDto {
    private String studentName;
    private String studentSurname;
    private String testName;
    private List<AnswerResultDto> answers = new ArrayList<>();
    private long trueCount;
    private long falseCount;
}

package org.batikan.test.system.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SolveTestDto {
    private Long studentId;
    private Long testId;
    private Map<String, Character> studentChoices = new HashMap<>();
}

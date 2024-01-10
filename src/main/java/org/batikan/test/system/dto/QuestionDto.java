package org.batikan.test.system.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class QuestionDto {
    private Long id;
    private String content;
    private Map<Character, String> choices = new HashMap<>();
    private Character rightChoice;
}

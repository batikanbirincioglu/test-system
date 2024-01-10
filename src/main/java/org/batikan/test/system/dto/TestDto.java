package org.batikan.test.system.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestDto {
    private Long id;
    private String name;
    private List<QuestionDto> questions = new ArrayList<>();
}

package org.batikan.test.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrUpdateTestDto {
    private String name;
    private List<Long> questionIds;
}

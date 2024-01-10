package org.batikan.test.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class TestQuestionMappingId implements Serializable {
    @Column(name = "TEST_ID")
    private Long testId;

    @Column(name = "QUESTION_ID")
    private Long questionId;
}

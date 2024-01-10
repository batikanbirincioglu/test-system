package org.batikan.test.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class StudentTestMappingId implements Serializable {
    @Column(name = "STUDENT_ID")
    private Long studentId;

    @Column(name = "TEST_ID")
    private Long testId;

    @Column(name = "QUESTION_ID")
    private Long questionId;
}

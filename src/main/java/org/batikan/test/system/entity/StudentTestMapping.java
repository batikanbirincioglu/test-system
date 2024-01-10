package org.batikan.test.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Entity(name = "StudentTestMapping")
@Table(name = "STUDENT_TEST_MAPPING")
public class StudentTestMapping implements Serializable {
    @EmbeddedId
    private StudentTestMappingId id = new StudentTestMappingId();

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "STUDENT_ID")
    @EqualsAndHashCode.Exclude
    private Student student;

    @ManyToOne
    @MapsId("testId")
    @JoinColumn(name = "TEST_ID")
    @EqualsAndHashCode.Exclude
    private Test test;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "QUESTION_ID")
    @EqualsAndHashCode.Exclude
    private Question question;

    @Column(name = "STUDENT_CHOICE")
    private Character studentChoice;
}

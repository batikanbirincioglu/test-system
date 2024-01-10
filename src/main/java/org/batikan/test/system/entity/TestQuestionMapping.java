package org.batikan.test.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Entity(name = "TestQuestionMapping")
@Table(name = "TEST_QUESTION_MAPPING")
public class TestQuestionMapping implements Serializable {
    @EmbeddedId
    private TestQuestionMappingId id = new TestQuestionMappingId();

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

    @Column(name = "NUMBER")
    private int number;
}

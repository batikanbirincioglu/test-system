package org.batikan.test.system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "Test")
@Table(name = "TEST")
public class Test implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentTestMapping> studentTestMappings = new ArrayList<>();

    @OneToMany(mappedBy = "test", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TestQuestionMapping> testQuestionMappings = new HashSet<>();

    public Question getQuestion(int number) {
        return testQuestionMappings.stream()
                .filter(mapping -> mapping.getNumber() == number)
                .findFirst()
                .map(mapping -> mapping.getQuestion())
                .orElse(null);
    }

    public int getQuestionNumber(Question question) {
        return testQuestionMappings.stream()
                .filter(mapping -> mapping.getQuestion().equals(question))
                .findFirst()
                .map(mapping -> mapping.getNumber())
                .orElse(null);
    }
}

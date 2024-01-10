package org.batikan.test.system.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.batikan.test.system.converter.ChoicesConverter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity(name = "Question")
@Table(name = "QUESTION")
public class Question implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CONTENT")
    private String content;

    @Convert(converter = ChoicesConverter.class)
    private Map<Character, String> choices = new HashMap<>();

    @Column(name = "RIGHT_CHOICE")
    private Character rightChoice;
}

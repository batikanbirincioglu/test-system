package org.batikan.test.system;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.batikan.test.system.entity.*;
import org.batikan.test.system.repository.QuestionRepository;
import org.batikan.test.system.repository.StudentRepository;
import org.batikan.test.system.repository.TestRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Transactional
@RequiredArgsConstructor
public class TestSystemMockDataInitializer {
    private final StudentRepository studentRepository;

    private final QuestionRepository questionRepository;

    private final TestRepository testRepository;

    public void initialize() {
	studentRepository.save(buildStudent("barry", "allen"));
	studentRepository.save(buildStudent("clark", "kent"));
	studentRepository.save(buildStudent("bruce", "wayne"));

	questionRepository.save(buildQuestion("What is the name of yellow thing up in the sky?",
		Map.of('A', "Moon",
			'B', "Sun",
			'C', "Mars",
			'D', "Pluton"),
		'B'));
	questionRepository.save(buildQuestion("In between which countries, cold war occurred?",
		Map.of('A', "Turkey - England",
			'B', "Germany - Russia",
			'C', "Banana Republic - Quandar",
			'D', "USA - Russia"),
		'D'));
	questionRepository.save(buildQuestion("What is 5 x 8 ?",
		Map.of('A', "39",
			'B', "38",
			'C', "40",
			'D', "42"),
		'C'));
	questionRepository.save(buildQuestion("What is name of the highest mountain on earth?",
		Map.of('A', "Everest",
			'B', "Uludag",
			'C', "Trivor",
			'D', "Muztagata"),
		'A'));

	buildTest("test-1", List.of(0, 2, 3), testRepository, questionRepository);
	buildTest("test-2", List.of(2, 0, 1), testRepository, questionRepository);

	buildStudentTestMapping(
		studentRepository.findByName("barry").get(),
		testRepository.findByName("test-2").get(),
		List.of('C', 'C', 'C', 'B'), studentRepository);
    }


    private Student buildStudent(String name, String surname) {
	Student student = new Student();
	student.setName(name);
	student.setSurname(surname);
	return student;
    }

    private Question buildQuestion(String content, Map<Character, String> choices, Character rightChoice) {
	Question question = new Question();
	question.setContent(content);
	question.setChoices(choices);
	question.setRightChoice(rightChoice);
	return question;
    }

    private Test buildTest(String name, List<Integer> questionIndexes, TestRepository testRepository, QuestionRepository questionRepository) {
	Test test = new Test();
	test.setName(name);
	test = testRepository.saveAndFlush(test);

	List<Question> allQuestions = questionRepository.findAll();
	List<Question> testQuestions = questionIndexes.stream().map(index -> allQuestions.get(index)).toList();
	int i = 1;
	for (Question testQuestion : testQuestions) {
	    TestQuestionMapping testQuestionMapping = new TestQuestionMapping();
	    testQuestionMapping.setTest(test);
	    testQuestionMapping.setQuestion(testQuestion);
	    testQuestionMapping.setNumber(i);
	    i++;
	    test.getTestQuestionMappings().add(testQuestionMapping);
	}
	testRepository.saveAndFlush(test);
	return test;
    }

    private void buildStudentTestMapping(Student student, Test test, List<Character> studentChoices, StudentRepository studentRepository) {
	StudentTestMapping studentTestMapping = new StudentTestMapping();
	studentTestMapping.setStudent(student);
	studentTestMapping.setTest(test);
	List<TestQuestionMapping> testQuestionMappings = test.getTestQuestionMappings().stream().sorted(
		Comparator.comparingInt(TestQuestionMapping::getNumber)).toList();
	int i = 0;
	for (TestQuestionMapping testQuestionMapping : testQuestionMappings) {
	    StudentTestMapping stm = new StudentTestMapping();
	    stm.setStudent(student);
	    stm.setTest(test);
	    stm.setQuestion(testQuestionMapping.getQuestion());
	    stm.setStudentChoice(studentChoices.get(i));
	    i++;
	    student.getStudentTestMappings().add(stm);
	}
	studentRepository.saveAndFlush(student);
    }
}

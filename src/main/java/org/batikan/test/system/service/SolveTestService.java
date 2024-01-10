package org.batikan.test.system.service;

import lombok.RequiredArgsConstructor;
import org.batikan.test.system.dto.AnswerResultDto;
import org.batikan.test.system.dto.TestResultDto;
import org.batikan.test.system.entity.Student;
import org.batikan.test.system.entity.StudentTestMapping;
import org.batikan.test.system.entity.Test;
import org.batikan.test.system.exception.BusinessError;
import org.batikan.test.system.exception.BusinessException;
import org.batikan.test.system.dto.SolveTestDto;
import org.batikan.test.system.repository.StudentRepository;
import org.batikan.test.system.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SolveTestService {
    private final StudentRepository studentRepository;

    private final TestRepository testRepository;

    @Transactional
    public void solveTest(SolveTestDto solveTestDto) {
	Student student = validateStudentExist(solveTestDto.getStudentId());
	Test test = validateTestExist(solveTestDto.getTestId());

	List<StudentTestMapping> alreadySolvedTestData = student.getStudentTestMappings()
		.stream()
		.filter(mapping -> mapping.getTest().getId().equals(solveTestDto.getTestId()))
		.toList();
	student.getStudentTestMappings().removeAll(alreadySolvedTestData);
	studentRepository.saveAndFlush(student);

	for (Map.Entry<String, Character> entry : solveTestDto.getStudentChoices().entrySet()) {
	    Integer questionNumber = Integer.valueOf(entry.getKey());
	    Character studentChoice = entry.getValue();
	    StudentTestMapping studentTestMapping = new StudentTestMapping();
	    studentTestMapping.setStudent(student);
	    studentTestMapping.setTest(test);
	    studentTestMapping.setQuestion(test.getQuestion(questionNumber));
	    studentTestMapping.setStudentChoice(studentChoice);
	    student.getStudentTestMappings().add(studentTestMapping);
	}

	studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public TestResultDto getTestResult(Long studentId, Long testId) {
	Student student = validateStudentExist(studentId);
	Test test = validateTestExist(testId);

	List<StudentTestMapping> studentTestMappings = student.getStudentTestMappings()
		.stream()
		.filter(mapping -> mapping.getTest().equals(test))
		.toList();
	if (studentTestMappings.isEmpty()) {
	    throw new BusinessException(BusinessError.STUDENT_DID_NOT_SOLVE_SUCH_TEST);
	}

	TestResultDto testResultDto = new TestResultDto();
	testResultDto.setStudentName(student.getName());
	testResultDto.setStudentSurname(student.getSurname());
	testResultDto.setTestName(studentTestMappings.get(0).getTest().getName());

	for (StudentTestMapping studentTestMapping : studentTestMappings) {
	    AnswerResultDto answer = new AnswerResultDto();
	    answer.setQuestionNumber(studentTestMapping.getTest().getQuestionNumber(studentTestMapping.getQuestion()));
	    answer.setStudentChoice(studentTestMapping.getStudentChoice());
	    answer.setRightChoice(studentTestMapping.getQuestion().getRightChoice());
	    answer.setCorrectlyAnswered(answer.getStudentChoice().equals(answer.getRightChoice()));
	    testResultDto.getAnswers().add(answer);
	}
	testResultDto.setTrueCount(testResultDto.getAnswers().stream().filter(answer -> answer.isCorrectlyAnswered()).count());
	testResultDto.setFalseCount(testResultDto.getAnswers().size() - testResultDto.getTrueCount());
	return testResultDto;
    }

    private Student validateStudentExist(Long studentId) {
	Student student = studentRepository.findById(studentId)
		.orElseThrow(() -> new BusinessException(BusinessError.STUDENT_NOT_FOUND));
	return student;
    }

    private Test validateTestExist(Long testId) {
	Test test = testRepository.findById(testId)
		.orElseThrow(() -> new BusinessException(BusinessError.TEST_NOT_FOUND));
	return test;
    }
}

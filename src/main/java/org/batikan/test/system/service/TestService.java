package org.batikan.test.system.service;

import lombok.RequiredArgsConstructor;
import org.batikan.test.system.entity.Test;
import org.batikan.test.system.entity.TestQuestionMapping;
import org.batikan.test.system.exception.BusinessError;
import org.batikan.test.system.exception.BusinessException;
import org.batikan.test.system.mapper.TestMapper;
import org.batikan.test.system.dto.CreateOrUpdateTestDto;
import org.batikan.test.system.dto.TestDto;
import org.batikan.test.system.entity.Question;
import org.batikan.test.system.repository.QuestionRepository;
import org.batikan.test.system.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestMapper testMapper;

    private final TestRepository testRepository;

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<TestDto> getAll() {
	return testRepository.findAll()
		.stream()
		.map(testMapper::toDto)
		.toList();
    }

    @Transactional(readOnly = true)
    public TestDto getById(Long id) {
	return testMapper.toDto(testRepository.findById(id).get());
    }

    @Transactional
    public TestDto create(CreateOrUpdateTestDto dto) {
	Test test = new Test();
	test.setName(dto.getName());

	List<Question> questions = questionRepository.findAllById(dto.getQuestionIds());
	int i = 1;
	for (Question question : questions) {
	    TestQuestionMapping testQuestionMapping = new TestQuestionMapping();
	    testQuestionMapping.setTest(test);
	    testQuestionMapping.setQuestion(question);
	    testQuestionMapping.setNumber(i);
	    test.getTestQuestionMappings().add(testQuestionMapping);
	    i++;
	}
	test = testRepository.save(test);
	return testMapper.toDto(test);
    }

    @Transactional
    public TestDto update(Long id, CreateOrUpdateTestDto dto) {
	Test test = testRepository.findById(id)
		.orElseThrow(() -> new BusinessException(BusinessError.TEST_NOT_FOUND));

	test.setName(dto.getName());
	test.getTestQuestionMappings().clear();

	List<Question> questions = questionRepository.findAllById(dto.getQuestionIds());
	if (questions.size() != dto.getQuestionIds().size()) {
	    throw new BusinessException(BusinessError.SOME_QUESTIONS_ARE_NOT_FOUND);
	}

	int i = 1;
	for (Question question : questions) {
	    TestQuestionMapping testQuestionMapping = new TestQuestionMapping();
	    testQuestionMapping.setTest(test);
	    testQuestionMapping.setQuestion(question);
	    testQuestionMapping.setNumber(i);
	    test.getTestQuestionMappings().add(testQuestionMapping);
	    i++;
	}
	test = testRepository.save(test);
	return testMapper.toDto(test);
    }

    @Transactional
    public void delete(Long id) {
	testRepository.deleteById(id);
    }
}

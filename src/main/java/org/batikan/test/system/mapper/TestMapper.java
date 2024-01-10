package org.batikan.test.system.mapper;

import org.batikan.test.system.entity.Test;
import org.batikan.test.system.dto.QuestionDto;
import org.batikan.test.system.dto.TestDto;
import org.batikan.test.system.entity.TestQuestionMapping;
import org.batikan.test.system.repository.QuestionRepository;
import org.batikan.test.system.repository.TestRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TestMapper {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;


    public TestDto toDto(Test entity) {
	TestDto testDto = new TestDto();
	testDto.setId(entity.getId());
	testDto.setName(entity.getName());
	List<QuestionDto> questionsSorted = entity.getTestQuestionMappings()
		.stream()
		.sorted(Comparator.comparing(TestQuestionMapping::getNumber))
		.map(TestQuestionMapping::getQuestion)
		.map(questionMapper::toDto)
		.toList();
	testDto.setQuestions(questionsSorted);
	return testDto;
    }
}

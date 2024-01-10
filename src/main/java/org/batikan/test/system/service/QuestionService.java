package org.batikan.test.system.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.batikan.test.system.Constants;
import org.batikan.test.system.exception.BusinessError;
import org.batikan.test.system.exception.BusinessException;
import org.batikan.test.system.mapper.QuestionMapper;
import org.batikan.test.system.dto.QuestionDto;
import org.batikan.test.system.entity.Question;
import org.batikan.test.system.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.batikan.test.system.Constants.POSSIBLE_CHOICES;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionMapper questionMapper;

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<QuestionDto> getAll() {
	return questionRepository.findAll()
		.stream()
		.map(questionMapper::toDto)
		.toList();
    }

    @Transactional(readOnly = true)
    public QuestionDto getById(Long id) {
	return questionMapper.toDto(questionRepository.findById(id).get());
    }

    @Transactional
    public QuestionDto create(QuestionDto dto) {
	validateContentNotEmpty(dto);
	validateQuestionChoiceCount(dto);
	validateQuestionChoiceMap(dto);
	validateRightChoice(dto);

	Question question = questionMapper.toEntity(dto);
	question = questionRepository.save(question);
	return questionMapper.toDto(question);
    }

    @Transactional
    public QuestionDto update(Long id, QuestionDto dto) {
	validateContentNotEmpty(dto);
	validateQuestionChoiceCount(dto);
	validateQuestionChoiceMap(dto);
	validateRightChoice(dto);

	Question question = questionRepository.findById(id)
		.orElseThrow(() -> new BusinessException(BusinessError.QUESTION_NOT_FOUND));

	question.setContent(dto.getContent());
	question.setChoices(dto.getChoices());
	question.setRightChoice(dto.getRightChoice());
	questionRepository.save(question);
	return questionMapper.toDto(question);
    }

    @Transactional
    public void delete(Long id) {
	questionRepository.deleteById(id);
    }

    private void validateQuestionChoiceCount(QuestionDto dto) {
	if (dto.getChoices().size() != Constants.QUESTION_CHOICE_COUNT) {
	    throw new BusinessException(BusinessError.QUESTIONS_CHOICE_COUNT_MISMATCH);
	}
    }

    private void validateContentNotEmpty(QuestionDto dto) {
	if (StringUtils.isEmpty(dto.getContent())) {
	    throw new BusinessException(BusinessError.QUESTION_CONTENT_NOT_EXIST);
	}
    }

    private void validateQuestionChoiceMap(QuestionDto dto) {
	if (dto.getChoices().isEmpty() || !dto.getChoices().keySet().equals(
		POSSIBLE_CHOICES)) {
	    throw new BusinessException(BusinessError.QUESTION_CHOICES_NOT_VALID);
	}
    }

    private void validateRightChoice(QuestionDto dto) {
	if (dto.getRightChoice() == null || !POSSIBLE_CHOICES.contains(dto.getRightChoice())) {
	    throw new BusinessException(BusinessError.RIGHT_CHOICE_NOT_VALID);
	}
    }
}

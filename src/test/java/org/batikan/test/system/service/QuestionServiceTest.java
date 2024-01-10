package org.batikan.test.system.service;

import org.batikan.test.system.dto.QuestionDto;
import org.batikan.test.system.entity.Question;
import org.batikan.test.system.exception.BusinessError;
import org.batikan.test.system.exception.BusinessException;
import org.batikan.test.system.mapper.QuestionMapper;
import org.batikan.test.system.repository.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private QuestionRepository questionRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void test_create_questionContentNotExist() {
	QuestionDto dto = new QuestionDto();
	BusinessException exception = Assertions.assertThrows(BusinessException.class,
		() -> questionService.create(dto));

	assertEquals(BusinessError.QUESTION_CONTENT_NOT_EXIST, exception.getBusinessError());
    }

    @Test
    public void test_create_questionChoiceCountMismatch() {
	QuestionDto dto = new QuestionDto();
	dto.setContent("laksdjflaksdfjlaskdfjasldf ?");
	dto.setChoices(Map.of('A', "1", 'B', "2", 'C', "3"));
	BusinessException exception = Assertions.assertThrows(BusinessException.class,
		() -> questionService.create(dto));

	assertEquals(BusinessError.QUESTIONS_CHOICE_COUNT_MISMATCH, exception.getBusinessError());
    }

    @Test
    public void test_create_questionChoicesNotValid() {
	QuestionDto dto = new QuestionDto();
	dto.setContent("laksdjflaksdfjlaskdfjasldf ?");
	dto.setChoices(Map.of('A', "1", 'B', "2", 'C', "3", 'X', "4"));
	BusinessException exception = Assertions.assertThrows(BusinessException.class,
		() -> questionService.create(dto));

	assertEquals(BusinessError.QUESTION_CHOICES_NOT_VALID, exception.getBusinessError());
    }

    @Test
    public void test_create_rightChoiceNotValid() {
	QuestionDto dto = new QuestionDto();
	dto.setContent("laksdjflaksdfjlaskdfjasldf ?");
	dto.setChoices(Map.of('A', "1", 'B', "2", 'C', "3", 'D', "4"));
	dto.setRightChoice('K');
	BusinessException exception = Assertions.assertThrows(BusinessException.class,
		() -> questionService.create(dto));

	assertEquals(BusinessError.RIGHT_CHOICE_NOT_VALID, exception.getBusinessError());
    }

    @Test
    public void test_create_success() {
	QuestionDto dto = new QuestionDto();
	dto.setContent("laksdjflaksdfjlaskdfjasldf ?");
	dto.setChoices(Map.of('A', "1", 'B', "2", 'C', "3", 'D', "4"));
	dto.setRightChoice('D');

	Question question = new Question();
	when(questionMapper.toEntity(eq(dto))).thenReturn(question);
	when(questionRepository.save(eq(question))).thenReturn(question);

	QuestionDto questionDto = questionService.create(dto);
	verify(questionRepository, times(1)).save(any(Question.class));
	verify(questionMapper, times(1)).toDto(any(Question.class));
    }

    @Test
    public void test_update_QuestionNotFound() {
	QuestionDto dto = new QuestionDto();
	dto.setId(11111L);
	dto.setContent("laksdjflaksdfjlaskdfjasldf ?");
	dto.setChoices(Map.of('A', "1", 'B', "2", 'C', "3", 'D', "4"));
	dto.setRightChoice('D');

	when(questionRepository.findById(eq(11111L))).thenReturn(
		Optional.ofNullable(null));

	BusinessException businessException = Assertions.assertThrows(BusinessException.class,
		() -> questionService.update(11111L, dto));
	assertEquals(BusinessError.QUESTION_NOT_FOUND, businessException.getBusinessError());
    }

    @Test
    public void test_update_success() {
	QuestionDto dto = new QuestionDto();
	dto.setId(11111L);
	dto.setContent("laksdjflaksdfjlaskdfjasldf ?");
	dto.setChoices(Map.of('A', "1", 'B', "2", 'C', "3", 'D', "4"));
	dto.setRightChoice('D');

	Question questionInDatabase = new Question();
	questionInDatabase.setId(dto.getId());
	questionInDatabase.setContent("alksdfj ?");
	questionInDatabase.setChoices(Map.of('A', "11", 'B', "22", 'C', "33", 'D', "44"));
	questionInDatabase.setRightChoice('B');

	when(questionRepository.findById(eq(dto.getId()))).thenReturn(
		Optional.ofNullable(questionInDatabase));

	questionService.update(dto.getId(), dto);
	verify(questionRepository, times(1)).save(ArgumentMatchers.argThat(
		question -> question.getRightChoice() == 'D'));
    }
}

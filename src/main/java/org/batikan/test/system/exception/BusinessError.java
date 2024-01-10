package org.batikan.test.system.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@AllArgsConstructor
public enum BusinessError {
    STUDENT_NOT_FOUND(-1, "Student is not found.", BAD_REQUEST),
    TEST_NOT_FOUND(-2, "Test is not found.", BAD_REQUEST),
    QUESTION_NOT_FOUND(-3, "Question is not found.", BAD_REQUEST),
    SOME_QUESTIONS_ARE_NOT_FOUND(-4, "Some questions are not found.", BAD_REQUEST),
    QUESTIONS_CHOICE_COUNT_MISMATCH(-5, "Questions must have fixed number of choices", BAD_REQUEST),
    STUDENT_DID_NOT_SOLVE_SUCH_TEST(-6, "The test given is not solved by the student yet.", BAD_REQUEST),
    QUESTION_CONTENT_NOT_EXIST(-7, "Content of the question is not defined.", BAD_REQUEST),
    QUESTION_CHOICES_NOT_VALID(-8, "Choices of the questions must be specific letters.", BAD_REQUEST),
    RIGHT_CHOICE_NOT_VALID(-9, "Right choice must be one of the specific letters.", BAD_REQUEST);

    private int errorCode;

    private String errorDescription;

    private HttpStatus errorStatus;

    public int getErrorCode() {
	return errorCode;
    }

    public String getErrorDescription() {
	return errorDescription;
    }

    public HttpStatus getErrorStatus() {
	return errorStatus;
    }
}

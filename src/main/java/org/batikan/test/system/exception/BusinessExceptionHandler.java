package org.batikan.test.system.exception;

import lombok.extern.slf4j.Slf4j;
import org.batikan.test.system.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handleException(
	    BusinessException exception) {
	log.error("Exception occurred", exception);
	return ResponseEntity.status(
			exception.getBusinessError().getErrorStatus())
		.body(Response.failure(exception));
    }
}

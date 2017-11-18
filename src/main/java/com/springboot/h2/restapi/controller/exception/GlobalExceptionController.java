package com.springboot.h2.restapi.controller.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.h2.restapi.model.exception.AvanueChallengeBusinessException;

/**
 * 
 * @author Vinicius Falcão
 *
 */
@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(AvanueChallengeBusinessException.class)
	@ResponseBody
	public Object handleBusinessException(AvanueChallengeBusinessException ex) {
		Map<String, Object> exceptionHandle = new HashMap<>();
		exceptionHandle.put("message", ex.getMessage());
		return new ResponseEntity<>(exceptionHandle, ex.getHttpStatus());
	}

}

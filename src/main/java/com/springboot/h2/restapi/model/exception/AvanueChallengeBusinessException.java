package com.springboot.h2.restapi.model.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 
 * @author Vinicius Falcão
 *
 */
@Getter
public class AvanueChallengeBusinessException extends RuntimeException {

	private static final long serialVersionUID = -5370604147132547916L;

	private HttpStatus httpStatus;

	public AvanueChallengeBusinessException(HttpStatus httpStatus, String message, Throwable t) {
		super(message, t);
		this.httpStatus = httpStatus;
	}

	public AvanueChallengeBusinessException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

}

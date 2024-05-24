package com.tools.dashboard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KpiNotFoundException extends RuntimeException{
	public KpiNotFoundException(String message) {
		super(message);
	}
}

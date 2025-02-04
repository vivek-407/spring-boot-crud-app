package com.cars.exceptions;

import com.cars.dto.ErrorDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GloableExceptionHandler {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ErrorDto handleNotFoundException(BaseException baseException) {
        // Preparing the custom error response here
        return new ErrorDto(baseException.getErrorMessage(), baseException.getErrorCode(), baseException.getDeveloperMessage());
    }
}

package com.example.auth.exception;

import java.time.LocalDateTime;
import java.util.*;

import com.example.auth.constants.Constants;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private String[] resolveFieldName(ParameterValidationResult result, MessageSourceResolvable error) {
        // ✅ For payload validation (field-level)
        if (error instanceof FieldError fe) {
            return new String[]{"payload", fe.getField()};
        }
        // ✅ For parameter validation
        return new String[]{"parameter", result.getMethodParameter().getParameterName()};
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(HandlerMethodValidationException ex) {
        List<Map<String, String>> errors = ex.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream()
                        .map(error -> {
                            String[] field = resolveFieldName(result, error);
                            return Map.of(
                                    Constants.TYPE, field[0],
                                    Constants.FIELD, field[1] ,
                                    Constants.MESSAGE, error.getDefaultMessage()
                                );
                            }
                        )
                ).toList();
        return buildResponse(HttpStatus.BAD_REQUEST, Constants.VALIDATION_FAILED, errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        List<Map<String, String>> errorList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    Map<String, String> map = new HashMap<>();
                    map.put(Constants.TYPE, "payload");
                map.put( Constants.FIELD, error.getField());
                map.put(Constants.MESSAGE, error.getDefaultMessage());
                    return map;
                })
                .toList();

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", errorList);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ConstraintViolationException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(MethodArgumentTypeMismatchException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, Constants.INVALID_PARAMETER + ex.getName());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, Constants.SOMETHING_WENT_WRONG);
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
        List<Map<String, String>> errors = new ArrayList<>();
        return buildResponse(status, message, errors);
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, List<Map<String, String>> errors) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(message, LocalDateTime.now(), errors), status);
    }
}

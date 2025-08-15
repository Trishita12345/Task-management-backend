package com.example.auth.exception;

import java.time.LocalDateTime;
import java.util.*;

import com.example.auth.constants.Constants;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
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

    private String resolveFieldName(ParameterValidationResult result, MessageSourceResolvable error) {
        // ✅ For payload validation (field-level)
        if (error instanceof FieldError fe) {
            return fe.getField();
        }
        // ✅ For parameter validation
        return result.getMethodParameter().getParameterName();
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getParameterValidationResults()
                .forEach(result -> result.getResolvableErrors()
                        .forEach(error -> {
                            String field = resolveFieldName(result, error);
                            errors.put(
                                    field, error.getDefaultMessage()
                                );
                            }
                        )
                );
        return buildResponse(HttpStatus.BAD_REQUEST, Constants.VALIDATION_FAILED, errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    errors.put(error.getField(), error.getDefaultMessage());
                });

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", errors);
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

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedRequest(AuthorizationDeniedException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, Constants.ACCESS_DENIED);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, Constants.SOMETHING_WENT_WRONG + " : " + ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
        Map<String, String> errors = new HashMap<>();
        return buildResponse(status, message, errors);
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, Map<String, String> errors) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(message, LocalDateTime.now(), errors), status);
    }
}

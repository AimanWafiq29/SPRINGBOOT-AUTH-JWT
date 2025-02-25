package com.example.belajar_auth.advices;

import com.example.belajar_auth.dto.response.BaseResponse;
import com.example.belajar_auth.dto.response.ErrorResponse;
import com.example.belajar_auth.exceptions.BadRequestException;
import com.example.belajar_auth.exceptions.NotAuthorizedException;
import com.example.belajar_auth.exceptions.NotFoundException;
import com.example.belajar_auth.exceptions.SystemErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<BaseResponse> handleBadRequestException(BadRequestException ex) {
        logError(ex, "Bad Request");
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException ex) {
        logError(ex, "Not Found");
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    protected ResponseEntity<BaseResponse> handleNotAuthorizedException(NotAuthorizedException ex) {
        logError(ex, "Not Authorized");
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SystemErrorException.class)
    protected ResponseEntity<BaseResponse> handleSystemErrorException(SystemErrorException ex) {
        logError(ex, "System Error");
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ErrorResponse.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .toList();

        BaseResponse baseResponse = BaseResponse.builder()
                .success(false)
                .message("Validation failed")
                .errors(errors.get(0)) // Ambil error pertama
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse> handleUnexpectedException(Exception ex) {
        logError(ex, "Unexpected Error");
        return buildErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logError(Exception ex, String logMessage) {
        log.error("{}: {} - {}", logMessage, ex.getClass().getSimpleName(), ex.getMessage(), ex);
    }

    private ResponseEntity<BaseResponse> buildErrorResponse(String message, HttpStatus statusCode) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .code(statusCode.value())
                .build();
        BaseResponse baseResponse = BaseResponse.builder()
                .success(false)
                .message(message)
                .errors(errorResponse)
                .build();
        return ResponseEntity.status(statusCode).body(baseResponse);
    }
}
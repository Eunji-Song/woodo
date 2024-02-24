package com.example.woodo.common.exception;

import com.example.woodo.common.apiresult.ApiResult;
import com.example.woodo.common.apiresult.ApiResultCode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // Not found
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotfoundException(NotFoundException ex) {
        log.info("NotFoundException : {}", ex.getMessage());

        ApiResult apiResult = ApiResult.error(ApiResultCode.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResult);
    }

    // Conflict
    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Object> handleConflictException(ConflictException ex) {
        log.info("ConflictException : {}", ex.getMessage());

        ApiResult apiResult = ApiResult.error(ApiResultCode.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResult);
    }

    // CUSTOM 유효성 검사
    @ExceptionHandler({InvalidDataException.class})
    protected ResponseEntity<Object> handleCustomInvalidDataException(Exception ex) {
        log.info("handleCustomInvalidDataException : {}", ex.getMessage());

        ApiResult apiResult = ApiResult.error(ApiResultCode.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResult);
    }

    // 입력한 JSON 문법 오류
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("HttpMessageNotReadableException : {}", ex.getMessage());

        String message = ex.getMessage();
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            message = invalidFormatException.getPath().get(0).getFieldName() + " 필드 값을 확인해주세요. ";
        }

        ApiResult apiResult = ApiResult.error(ApiResultCode.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResult);
    }


    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return super.handleHandlerMethodValidationException(ex, headers, status, request);
    }

    // 유효성 검사 실패
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("MethodArgumentNotValidException : {}", ex.getMessage());

        String errMsg = ApiResultCode.BAD_REQUEST.getMessage();

        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasErrors()) {
            // 오류 필드 내용들 중 첫번째 오류 메시지 리턴
            errMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        ApiResult apiResult = ApiResult.error(ApiResultCode.BAD_REQUEST, errMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResult);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.info("MethodArgumentTypeMismatchException : {}", ex.getMessage());

        ApiResult apiResult = ApiResult.error(ApiResultCode.BAD_REQUEST, "잘못된 타입의 데이터가 입력되었습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResult);
    }


    // DB 예외 처리
    @ExceptionHandler({DataAccessException.class})
    protected ResponseEntity<Object> handleDatabaseException(DataAccessException ex) {
        log.info("DataAccessException : {}", ex.getMessage());

        ApiResult apiResult = ApiResult.error(ApiResultCode.ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResult);
    }

    // SQL 예외
    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<Object> handleSQLException(SQLException ex) {
        log.info("SQLException : {}", ex.getMessage());

        ApiResult apiResult = ApiResult.error(ApiResultCode.ERROR, "SQL error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResult);
    }

    // 올바르지 않은 HTTP Method로 전송한 경우
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpRequestMethodNotSupportedException caught: {}", ex.getMessage());

        ApiResult apiResult = ApiResult.error(ApiResultCode.ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResult);
    }


}

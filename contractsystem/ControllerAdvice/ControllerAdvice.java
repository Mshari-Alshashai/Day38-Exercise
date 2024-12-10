package com.example.contractsystem.ControllerAdvice;

import com.example.contractsystem.ApiResponse.ApiException;
import com.example.contractsystem.ApiResponse.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLIntegrityConstraintViolationException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

        @ExceptionHandler(value = ApiException.class)
        public ResponseEntity<ApiResponse> ApiException(ApiException apiException) {
            String message = apiException.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        @ExceptionHandler(value = MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse> MethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
            String message = methodArgumentNotValidException.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        @ExceptionHandler(value = ConstraintViolationException.class)
        public ResponseEntity<ApiResponse> ConstraintViolationException(ConstraintViolationException e) {
            String msg =e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
        public ResponseEntity<ApiResponse> SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
            String msg=e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class )
        public ResponseEntity<ApiResponse> InvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
            String msg=e.getMessage();
            return ResponseEntity.status(200).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = DataIntegrityViolationException.class)
        public ResponseEntity<ApiResponse> DataIntegrityViolationException(DataIntegrityViolationException e){
            String msg=e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiResponse> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<ApiResponse> NoHandlerFoundException(NoHandlerFoundException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<ApiResponse> MissingServletRequestParameter(MissingServletRequestParameterException e) {
            String msg= e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(MissingPathVariableException.class)
        public ResponseEntity<ApiResponse> MissingPathVariable(MissingPathVariableException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse> GenericException(Exception e) {
            String msg = e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
        public ResponseEntity<ApiResponse> HttpMediaTypeNotSupportedException (HttpMediaTypeNotSupportedException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(ServletRequestBindingException.class)
        public ResponseEntity<ApiResponse> ServletRequestBindingException(ServletRequestBindingException e) {
            String msg= e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(EmptyResultDataAccessException.class)
        public ResponseEntity<ApiResponse> EmptyResultDataAccessException(EmptyResultDataAccessException e) {
            String msg = e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(TransactionSystemException.class)
        public ResponseEntity<ApiResponse> TransactionSystemException(TransactionSystemException e) {
            String msg= e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ApiResponse> EntityNotFoundException(EntityNotFoundException e) {
            String msg= e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public ResponseEntity<ApiResponse> MaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
            String msg= e.getMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }
}



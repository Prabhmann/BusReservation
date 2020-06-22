package com.paypal.busreservation.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<Object> handleCityNotFoundException(
      ServiceException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllExceptions(
      ServiceException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

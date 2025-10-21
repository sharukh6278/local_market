package org.spring.security.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
  private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorMessage> runTimeExceptionHandler(RuntimeException ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(500, ex.getMessage(), ex.toString());
        logger.info("ControllerExceptionHandler : runTimeExceptionHandler() : {}", ex.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> exceptionHandler(RuntimeException ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(500, ex.getMessage(), ex.toString());
        logger.info("ControllerExceptionHandler : exceptionHandler() : {}", ex.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ApnaShopException.class})
    public ResponseEntity<ErrorMessage> fridayExceptionHandler(ApnaShopException ex, WebRequest request) {
        logger.info("ControllerExceptionHandler : fridayExceptionHandler() : {}", ex.toString());
        ErrorMessage errorMessage = new ErrorMessage(ex.getStatusCode(), ex.getMessage(), ex.getDescription());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpClientErrorException.class})
    public ResponseEntity<ErrorMessage> httpClientErrorExceptionHandler(HttpClientErrorException ex, WebRequest request) {
        logger.info("ControllerExceptionHandler : fridayExceptionHandler() : {}", ex.toString());
        ErrorMessage errorMessage = new ErrorMessage(500, ex.getMessage(), ex.toString());
        return new ResponseEntity<>(errorMessage, ex.getStatusCode());
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<ErrorMessage> expiredJwtExceptionHandler(ExpiredJwtException ex, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(500, "Session got Expired please Login Again", ex.getMessage());
        logger.info("ControllerExceptionHandler : expiredJwtExceptionHandler() : {}", ex.toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
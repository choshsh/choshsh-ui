package com.choshsh.choshshui.config;

import java.nio.file.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> runtimeError(
      final RuntimeException e) {
    log.error("error", e);
    return formatErrorMsg("권한 에러", e);

  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> authError(
      final AccessDeniedException e) {
    log.error("error", e);
    return formatErrorMsg("권한 에러", e);
  }

  @ExceptionHandler({HttpClientErrorException.class})
  public ResponseEntity<Object> clientError(final Exception e) {
    log.info(e.getClass().getName());
    log.error("error", e);
    return formatErrorMsg("클라이언트 에러", e);
  }

  @ExceptionHandler({HttpServerErrorException.class})
  public ResponseEntity<Object> serverError(final Exception e) {
    log.info(e.getClass().getName());
    log.error("error", e);
    return formatErrorMsg("서버 에러", e);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> commonError(final Exception e) {
    log.info(e.getClass().getName());
    log.error("error", e);
    return formatErrorMsg("에러", e);
  }

  private ResponseEntity<Object> formatErrorMsg(String errorAlias, final Exception e) {
    return ResponseEntity.badRequest().body("[" + errorAlias + "]\n상세: " + e.getMessage());
  }

}

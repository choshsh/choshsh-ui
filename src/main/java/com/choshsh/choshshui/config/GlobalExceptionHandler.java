package com.choshsh.choshshui.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  static final String ERROR_MESSAGE_CUSTOM = "Error ===> ";

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> status_400(
      final RuntimeException error) {
    log.error("error", error);
    return ResponseEntity.badRequest().body(ERROR_MESSAGE_CUSTOM + "런타임 에러");

  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> status_401(
      final AccessDeniedException ex) {
    log.error("error", ex);
    return ResponseEntity.badRequest().body(ERROR_MESSAGE_CUSTOM + "권한 에러");
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> status_500(final Exception ex) {
    log.info(ex.getClass().getName());
    log.error("error", ex);
    return ResponseEntity.badRequest().body(ERROR_MESSAGE_CUSTOM + "내부 에러");
  }

}

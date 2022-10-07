package com.choshsh.choshshui.config;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler({AccessDeniedException.class})
  public Map<String, Object> authError(final AccessDeniedException e) {
    return getCustomError("AccessDenied", e.getMessage(), e);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({HttpClientErrorException.class})
  public Map<String, Object> clientError(final Exception e) {
    return getCustomError(e.getMessage(), "", e);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({HttpServerErrorException.class})
  public Map<String, Object> serverError(final Exception e) {
    return getCustomError("INTERNAL_SERVER_ERROR", e.getMessage(), e);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
  public Map<String, Object> commonError(final Exception e) {
    return getCustomError(e.getMessage(), "", e);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  public Map<String, Object> handleValidationExceptions(@NotNull BindException e) {
    List<String> details = new ArrayList<>();
    e.getBindingResult().getAllErrors().forEach((error) -> {
      details.add(error.getDefaultMessage());
    });
    return getCustomError("invalid parameter", details, e);
  }

  private ResponseEntity<Object> formatErrorMsg(String errorAlias, final Exception e) {
    return ResponseEntity.badRequest().body("[" + errorAlias + "]\n상세: " + e.getMessage());
  }

  private Map<String, Object> getCustomError(String msg, Object detail, Exception e){
    log.info(e.getClass().getName());
    log.error("error", e);

    Map<String, Object> error = new LinkedHashMap<>();
    error.put("timestamp", LocalDateTime.now());
    error.put("msg", msg);
    error.put("details", detail);
    return error;
  }
}

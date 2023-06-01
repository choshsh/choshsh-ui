package com.choshsh.choshshui.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class HttpLogInterceptor implements HandlerInterceptor {

  private long latency;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    latency = System.currentTimeMillis();
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    log.info("{ method: \"{}\", statusCode: \"{}\", latency: \"{}ms\", uri: \"{}\" }"
        , request.getMethod(), response.getStatus()
        , System.currentTimeMillis() - latency, request.getRequestURI());
  }

}

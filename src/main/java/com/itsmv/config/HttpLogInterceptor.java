package com.itsmv.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class HttpLogInterceptor implements HandlerInterceptor {

    private long timestampPre;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        timestampPre = System.currentTimeMillis();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex)
            throws Exception {
        log.info("{ method: \"{}\", statusCode: \"{}\", latency: \"{} ms\", uri: \"{}\" }"
                , req.getMethod(), res.getStatus(), System.currentTimeMillis() - timestampPre, req.getRequestURI());
    }
}
package com.francefootball.niceequipefootballapi.config.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class RequestResponseLoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void logRequest(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("""
            [Request]
            Method    : {}
            Endpoint  : {}
            Params    : {}
            Headers   : {}
            Arguments : {}
            """,
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                getHeaders(request),
                Arrays.toString(joinPoint.getArgs())
        );
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logResponse(Object result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("""
            [Response]
            Endpoint  : {}
            Status    : {}
            Response  : {}
            """,
                request.getRequestURI(),
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse().getStatus(),
                result
        );
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void logException(Exception ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.error("""
            [Error]
            Endpoint  : {}
            Exception : {}
            Message   : {}
            """,
                request.getRequestURI(),
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
    }

    private String getHeaders(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                headers.append(headerName).append(": ").append(request.getHeader(headerName)).append(", ")
        );
        return headers.toString();
    }
}

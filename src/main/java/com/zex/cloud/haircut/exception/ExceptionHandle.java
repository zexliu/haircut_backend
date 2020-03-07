package com.zex.cloud.haircut.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandle {


    @ExceptionHandler(value = {AuthenticationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handle401(AuthenticationException e, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        response.setError(e.getClass());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setTimestamp(LocalDateTime.now());
        return response;
    }
    @ExceptionHandler(value = {ExistsException.class,NotSupportException.class,ServerException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle500(Exception e, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        response.setError(e.getClass());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setTimestamp(LocalDateTime.now());
        return response;
    }


    @ExceptionHandler(value = {ForbiddenException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handle403(ForbiddenException e, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        response.setError(e.getClass());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handle404(NotFoundException e, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        response.setError(e.getClass());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(LocalDateTime.now());
        return response;
    }


    @ExceptionHandler(value = {ParameterException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle400(ParameterException e, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        response.setError(e.getClass());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(LocalDateTime.now());
        return response;
    }


}

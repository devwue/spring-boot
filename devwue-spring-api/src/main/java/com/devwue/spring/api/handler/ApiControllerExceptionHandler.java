package com.devwue.spring.api.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ApiControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request, Exception exception) {
        write(exception);
        return new ResponseEntity<>(errorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected void write(Exception e) {
        log.debug("exception: {}, cause: {} , message: {}", e.getClass(), e.getCause(), e.getMessage());
        log.debug("{}", e.getLocalizedMessage());
        log.debug(e.getStackTrace().toString());
        log.debug("==========");
    }

    private Map errorResponse(Exception e, HttpStatus code) {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        map.put("code", code.value());
        data.put("error_message", e.getLocalizedMessage());
        map.put("data", data);
        return map;
    }
}

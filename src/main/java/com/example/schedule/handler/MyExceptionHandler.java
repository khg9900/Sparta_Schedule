package com.example.schedule.handler;

import com.example.schedule.exception.InvalidPasswordException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    // 비밀번호가 일치하지 않을 때
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException ex){
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // 일정 식별자가 존재하지 않을 때
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex){
        return new ResponseEntity<String>("요청하신 일정이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }

}

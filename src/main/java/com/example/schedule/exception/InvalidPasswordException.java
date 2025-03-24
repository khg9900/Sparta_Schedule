package com.example.schedule.exception;

// 일정 수정, 삭제시 비밀번호가 일치하지 않으면 발생하는 에러
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

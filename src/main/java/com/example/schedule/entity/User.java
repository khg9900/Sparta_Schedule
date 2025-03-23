package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {

    private Long userId; // 작성자 식별자. DB에서 자동 생성
    private String email; // 이메일. unique
    private Long password; // 비밀번호
    private String name; // 작성자명
    private LocalDateTime userCreatedAt; // 등록일. DB에서 자동 생성
    private LocalDateTime userUpdatedAt; // 수정일. DB에서 자동 생성

    public User(String email, Long password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}

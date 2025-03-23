package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long scheduleId; // 일정 식별자. DB에서 자동 생성
    private Long userCode; // 작성자 ID. FK
    private String task; // 할 일
    private LocalDateTime scheduleCreatedAt; // 작성일. DB에서 자동 생성
    private LocalDateTime scheduleUpdatedAt; // 수정일. DB에서 자동 생성

    public Schedule(String task) {
        this.task = task;
    }

}

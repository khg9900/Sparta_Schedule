package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String task;
    private String name;
    private Long password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Schedule(String task, String name, Long password) {
        this.task = task;
        this.name = name;
        this.password = password;
    }

    public Schedule(Long id, String task, String name, LocalDateTime createAt, LocalDateTime updateAt){
        this.id = id;
        this.task = task;
        this.name = name;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

}

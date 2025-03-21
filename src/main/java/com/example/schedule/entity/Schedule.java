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

}

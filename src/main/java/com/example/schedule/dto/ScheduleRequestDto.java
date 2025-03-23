package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private String email;
    private Long password;
    private String name;
    private String task;

}

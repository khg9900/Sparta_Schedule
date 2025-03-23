package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long scheduleId;
    private String email;
    private String name;
    private String task;
    private LocalDateTime scheduleCreatedAt;
    private LocalDateTime scheduleUpdatedAt;

}

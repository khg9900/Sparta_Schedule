package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.time.LocalDateTime;

public interface ScheduleRepository {

    // 일정 생성
    ScheduleResponseDto saveSchedule(Schedule schedule);

}

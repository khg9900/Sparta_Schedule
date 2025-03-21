package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    // 일정 생성
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    // 전체 일정 조회
    List<ScheduleResponseDto> findAllSchedule(LocalDate findDate, String findName);

    // 선택 일정 조회
    ScheduleResponseDto findScheduleById(Long id);
}

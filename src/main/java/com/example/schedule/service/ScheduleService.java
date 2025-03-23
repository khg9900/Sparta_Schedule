package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    // 일정 생성
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    // 전체 일정 조회
    List<ScheduleResponseDto> findAllSchedules(LocalDate findScheduleUpdatedAt, Long findUserId);

    // 선택 일정 조회
    ScheduleResponseDto findScheduleById(Long id);

    // 선택 일정 수정
    ScheduleResponseDto updateSchedule(Long id, Long password, String name, String task);

    // 선택 일정 삭제
    void deleteSchedule(Long id);
}

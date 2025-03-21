package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    // 일정 생성
    ScheduleResponseDto saveSchedule(Schedule schedule);

    // 전체 일정 조회
    List<ScheduleResponseDto> findAllSchedule(LocalDate findDate, String findName);

    // 선택 일정 조회
    Schedule findScheduleById(Long id);

    // 선택 일정 수정
    int updateSchedule(Long id, String task, String name);

    // 선택 일정 삭제
    int deleteSchedule(Long id);

}

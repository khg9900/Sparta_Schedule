package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    // 일정 생성
    ScheduleResponseDto saveSchedule(Schedule schedule, User user);

    // 전체 일정 조회
    List<ScheduleResponseDto> findAllSchedule(LocalDate findScheduleUpdatedAt, Long findUserId, String pageSize, String pageOffset);
    // 선택 일정 조회
    ScheduleResponseDto findScheduleById(Long id);

    // 선택 일정 수정
    int updateSchedule(Long id, Long password, String name, String task);

    // 선택 일정 삭제
    int deleteSchedule(Long id, Long password);

}

package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    // 1. 속성
    private final ScheduleRepository scheduleRepository;

    // 2. 생성자
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 3. 메서드
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        // 요청받은 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(dto.getTask(), dto.getName(), dto.getPassword());

        // DB 저장
        return scheduleRepository.saveSchedule(schedule);
    }

}

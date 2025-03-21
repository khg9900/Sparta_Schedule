package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    // 1. 속성
    private final ScheduleRepository scheduleRepository;

    // 2. 생성자
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 3. 메서드
    @Override // 일정 생성
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        // 요청받은 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(dto.getTask(), dto.getName(), dto.getPassword());

        // DB 저장
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override // 전체 일정 조회
    public List<ScheduleResponseDto> findAllSchedule(LocalDate findDate, String findName) {
        // DB 조회
        return scheduleRepository.findAllSchedule(findDate, findName);
    }

    @Override // 선택 일정 조회
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule schedule =  scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

}

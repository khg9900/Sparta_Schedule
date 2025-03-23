package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

        // 요청받은 데이터로 객체 생성
        Schedule schedule = new Schedule(dto.getTask());
        User user = new User(dto.getEmail(), dto.getPassword(), dto.getName());

        return scheduleRepository.saveSchedule(schedule, user);
    }

    @Override // 전체 일정 조회
    public List<ScheduleResponseDto> findAllSchedules(LocalDate findScheduleUpdatedAt, Long findUserId) {
        return scheduleRepository.findAllSchedule(findScheduleUpdatedAt, findUserId);
    }

    @Override // 선택 일정 조회
    public ScheduleResponseDto findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, Long password, String name, String task) {
        // 비밀번호 일치하지 않으면 에러


        int updateRow = scheduleRepository.updateSchedule(id, name, task);

        // 업데이트할 id가 없으면 에러 발생
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return scheduleRepository.findScheduleById(id);
    }

    @Override
    public void deleteSchedule(Long id) {
        int deleteRow = scheduleRepository.deleteSchedule(id);

        // 삭제할 id가 없으면 에러 발생
        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

}

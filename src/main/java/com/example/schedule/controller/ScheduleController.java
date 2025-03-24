package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    // 1. 속성
    private final ScheduleService scheduleService;

    // 2. 생성자
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 3. 메서드
    @PostMapping // 일정 생성
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping // 전체 일정 조회
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
            // 일정 수정일과 작성자 ID를 쿼리 파라미터로 입력받고(필수X), 해당 조건으로 전체 일정 조회 가능
            @RequestParam(required = false) LocalDate findScheduleUpdatedAt,
            @RequestParam(required = false) Long findUserId,
            @RequestParam(defaultValue = "1") Long pageNumber,
            @RequestParam(defaultValue = "3") Long pageSize
            ) {
        return new ResponseEntity<>(scheduleService.findAllSchedules(findScheduleUpdatedAt, findUserId, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}") // 일정 ID로 일정 조회
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}") // 수정
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getPassword(), dto.getName(), dto.getTask()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // 삭제
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

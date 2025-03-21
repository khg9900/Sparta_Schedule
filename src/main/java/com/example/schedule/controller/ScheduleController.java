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
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto dto){
        // 요청 -> RequestDto -> Service Layer / Service Layer -> ResponseDto -> 응답
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping // 전체 일정 조회
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedule(
            @RequestParam(required = false) LocalDate findDate,
            @RequestParam(required = false) String findName
            ){

        return new ResponseEntity<>(scheduleService.findAllSchedule(findDate, findName), HttpStatus.OK);
    }

    @GetMapping("/{id}") // 선택 일정 조회
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id){

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

}

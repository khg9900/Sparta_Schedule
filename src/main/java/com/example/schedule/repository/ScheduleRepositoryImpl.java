package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    // 1. 속성
    private final JdbcTemplate jdbcTemplate;

    // 2. 생성자
    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        // INSERT Query 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id", "createAt", "updateAt");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule.getTask());
        parameters.put("name", schedule.getName());
        parameters.put("password", schedule.getPassword());

        // 저장 후 생성된 key값 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));


        LocalDateTime createAt = jdbcTemplate.queryForObject(
                "SELECT createAt FROM schedule WHERE id = ?",
                LocalDateTime.class,
                key
        );

        LocalDateTime updateAt = jdbcTemplate.queryForObject(
                "SELECT updateAt FROM schedule WHERE id = ?",
                LocalDateTime.class,
                key
        );


        return new ScheduleResponseDto(key.longValue(), schedule.getTask(), schedule.getName(), createAt, updateAt);
    }
}

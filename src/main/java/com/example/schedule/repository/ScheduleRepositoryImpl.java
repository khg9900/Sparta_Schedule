package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    // 1. 속성
    private final JdbcTemplate jdbcTemplate;

    // 2. 생성자
    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override // 일정 생성
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

    @Override // 전체 일정 조회
    public List<ScheduleResponseDto> findAllSchedule(LocalDate findDate, String findName) {

        if (findDate != null & findName != null) {
            return jdbcTemplate.query("SELECT * FROM schedule WHERE DATE(updateAt) = ? AND name = ? ORDER BY updateAt DESC", ScheduleRowMapper(), findDate, findName);
        } else if (findDate != null & findName == null) {
            return jdbcTemplate.query("SELECT * FROM schedule WHERE DATE(updateAt) = ? ORDER BY updateAt DESC", ScheduleRowMapper(), findDate);
        } else if (findDate == null & findName != null) {
            return jdbcTemplate.query("SELECT * FROM schedule WHERE name = ? ORDER BY updateAt DESC", ScheduleRowMapper(), findName);
        }
        return jdbcTemplate.query("SELECT * FROM schedule ORDER BY updateAt DESC", ScheduleRowMapper());

    }

    @Override
    public Schedule findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", ScheduleRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }

    @Override
    public int updateSchedule(Long id, String task, String name) {
        return jdbcTemplate.update("UPDATE schedule SET task = ?, name = ?  WHERE id = ?", task, name, id);
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

    private RowMapper<ScheduleResponseDto> ScheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("name"),
                        rs.getTimestamp("createAt").toLocalDateTime(),
                        rs.getTimestamp("updateAt").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> ScheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("name"),
                        rs.getTimestamp("createAt").toLocalDateTime(),
                        rs.getTimestamp("updateAt").toLocalDateTime()
                );
            }
        };
    }

}

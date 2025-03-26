package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    public ScheduleResponseDto saveSchedule(Schedule schedule, User user) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        // schedule 생성
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id", "created_at", "updated_at");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_code", getUserCode(user));
        parameters.put("task", schedule.getTask());

        // 저장 후 생성된 key값 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findScheduleById(key.longValue());
    }

    @Override // 전체 일정 조회
    public List<ScheduleResponseDto> findAllSchedule(LocalDate findScheduleUpdatedAt, Long findUserId, String pageSize, String pageOffset) {
        // 공통 query
        String selectQuery = "SELECT schedule.id, email, name, task, schedule.created_at, schedule.updated_at from schedule, user" +
                        " WHERE user.id = schedule.user_code";
        String sortQuery = " ORDER BY updated_at DESC LIMIT " + pageSize + " OFFSET " + pageOffset;

        // 조회 조건별로 return
        if (findScheduleUpdatedAt != null & findUserId != null) {
            return jdbcTemplate.query(selectQuery + " AND user.id = ? AND DATE(schedule.updated_at) = ?" + sortQuery + "Limit", ResponseRowMapper(), findScheduleUpdatedAt, findUserId);
        } else if (findScheduleUpdatedAt != null & findUserId == null) {
            return jdbcTemplate.query(selectQuery + " AND DATE(schedule.updated_at) = ?" + sortQuery, ResponseRowMapper(), findScheduleUpdatedAt);
        } else if (findUserId != null) {
            return jdbcTemplate.query(selectQuery + " AND user.id = ?"  + sortQuery, ResponseRowMapper(), findUserId);
        }
        return jdbcTemplate.query(selectQuery + sortQuery, ResponseRowMapper());
    }

    @Override // 선택 일정 조회
    public ScheduleResponseDto findScheduleById(Long id) {
        return jdbcTemplate.queryForObject("select schedule.id, email, name, task, schedule.created_at, schedule.updated_at from schedule, user where user.id = schedule.user_code AND schedule.id = ?", ResponseRowMapper(), id);
    }

    @Override // 선택 일정 수정
    public void updateSchedule(Long id, Long password, String name, String task) {
        isCorrectPassword(id, password);
        jdbcTemplate.update("UPDATE schedule, user SET task = ?, name = ?  WHERE user.id = schedule.user_code AND schedule.id = ?", task, name, id);
    }

    @Override // 선택 일정 삭제
    public void deleteSchedule(Long id, Long password) {
        isCorrectPassword(id, password);
        jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

    // user 테이블에 user 데이터 생성 후 userId 반환
    public Long getUserCode(User user) {
        // email(unique)가 DB에 존재하는지 확인
        List<Long> userId = jdbcTemplate.query("SELECT id FROM user WHERE email = ?", UserIdMapper(), user.getEmail());

        if (userId.isEmpty()) { // 없는 경우 user 새로 생성
            jdbcTemplate.update("INSERT INTO user (email, password, name) VALUES (?, ?, ?)", user.getEmail(), user.getPassword(), user.getName());
        } else {
            // DB에 저장된 작성명과 입력된 작성자명이 동일한지 확인
            List<String> userName = jdbcTemplate.query("SELECT name FROM user WHERE email = ?", UserNameMapper(), user.getEmail());
            if (!user.getName().equals(userName.get(0))) {
                // 불일치할 경우 email 중복값 삽입 불가
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
        // DB에서 작성자 식별자 가져오기
        return jdbcTemplate.queryForObject("SELECT id FROM user WHERE email = ?", Long.class, user.getEmail());
    }


    public void isCorrectPassword(Long id, Long password) {
        String query = "select password = ? from user where id = (select user_code from schedule where id = ?)";
        Long result = jdbcTemplate.queryForObject(query, Long.class, password, id);
        if (result == 0) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 쿼리 결괏값을 ScheduleResponseDto 객체로 변환
    private RowMapper<ScheduleResponseDto> ResponseRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("task"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Long> UserIdMapper() {
        return new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong("id");
            }
        };
    }

    private RowMapper<String> UserNameMapper() {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("name");
            }
        };
    }

}

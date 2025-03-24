package com.example.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @Email(message="올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotNull
    private Long password;
    @Size(min=2, message="이름을 두 글자 이상 입력해주세요.")
    private String name;
    @Size(max=200, message="최대 200자 입력 가능")
    @NotBlank
    private String task;

}

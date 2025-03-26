# 내일배움캠프 CH3 일정 관리 앱 만들기 과제

## 과제 소개
+ 목표 : 간단한 일정 관리 앱을 구현하며 Spring Boot, JDBC 익히기
+ 기간 : 25.03.21 ~ 03.25
+ 기술 스택
	
    + 백엔드 : Spring Boot (Java)
    + 데이터베이스 : MySQL
    + 데이터 액세스 : JDBC
    + 빌드 도구 : Gradle
    
+ 개발 환경: IntelliJ IDEA
+ 버전 관리: Git & Github

## 파일 구조
```
src/
└── main/
    └── java/
        └── com/example/schedule/
            ├── controller/
            │   └── ScheduleController.java
            │
            ├── dto/
            │   ├── ScheduleRequestDto.java
            │   └── ScheduleResponseDto.java
            │
            ├── entity/
            │   ├── Schedule.java
            │   └── User.java
            │
            ├── exception/
            │   └── InvalidPasswordException.java
            │
            ├── handler/
            │   └── MyExceptionHandler.java
            │
            ├── repository/
            │   ├── ScheduleRepository.java
            │   └── ScheduleRepositoryImpl.java
            │
            ├── service/
            │   ├── ScheduleService.java
            │   └── ScheduleServiceImpl.java
            │
            └── ScheduleApplication.java
```

## 주요 기능
1. 일정 생성
 + 이메일, 비밀번호, 이름, 할일 정보로 일정을 생성합니다.
 + 이때, 이메일이 동일한 작성자를 생성할 수 없습니다.

2. 전체 일정 조회
  + 전체 일정을 수정 날짜, 작성자 아이디를 기준으로 조회할 수 있습니다.
  + 조회시 페이지네이션이 적용되어 한 페이지에서 볼 일정 개수와 보고 싶은 페이지 번호를 설정할 수 있습니다. 

3. 선택 일정 조회
  + 일정 아이디를 통해 단건 일정을 조회할 수 있습니다.

4. 선택 일정 수정
+ 일정 아이디를 통해 단건 일정의 작성자명과 할 일을 수정할 수 있습니다.
+ 이때, 반드시 비밀번호가 일치해야 합니다.

5. 선택 일정 삭제
+ 일정 아이디를 통해 단건 일정을 삭제할 수 있습니다.
+ 이때, 반드시 비밀번호가 일치해야 합니다.


## API 명세서
<img width="1082" src="https://github.com/user-attachments/assets/3516f6d7-8f74-4fb8-8cbd-e633936ed4ea" />

## ERD
<img width="500" src="https://github.com/user-attachments/assets/1f61ce5b-b5d1-4af2-8b07-652b6af6b0cd"/>

## SQL Schema
```sql
  create table user
(
    id         bigint auto_increment comment '작성자 식별자'
        primary key,
    email      varchar(50)                        null comment '이메일',
    password   int                                null comment '비밀번호',
    name       varchar(10)                        not null comment '이름',
    created_at datetime default CURRENT_TIMESTAMP null comment '등록일',
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '수정일',
    constraint `unique`
        unique (email)
);

create table schedule
(
    id         bigint auto_increment comment '일정 식별자'
        primary key,
    user_code  bigint                             null comment '작성자 아이디 (FK)',
    task       varchar(200)                       not null comment '할일',
    created_at datetime default CURRENT_TIMESTAMP null comment '작성일',
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '수정일',
    constraint schedule_user_id_fk
        foreign key (user_code) references user (id)
            on update cascade on delete cascade
);
  ```




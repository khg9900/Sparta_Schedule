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



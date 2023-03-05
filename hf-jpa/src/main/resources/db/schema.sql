create table user_info
(
    id       bigint auto_increment primary key not null,
    name     varchar(255)                      not null,
    birthday datetime                          not null,
    remark   varchar(2048) default null
);


create database if not exists smlv;

CREATE TABLE if not exists smlv.user (
    id VARCHAR(100) NOT NULL PRIMARY KEY COMMENT 'Primary Key',
    alias VARCHAR(50) NOT NULL COMMENT 'user name',
    email VARCHAR(200) NOT NULL UNIQUE COMMENT 'user credential',
    birth_date DATE null COMMENT 'user birthday',
    gender CHAR(1) null COMMENT 'users biological gender',
    password VARCHAR(200) NOT NULL COMMENT 'user password',
    verified BOOLEAN not null DEFAULT FALSE COMMENT 'user has email verification',
    two_fa_seed VARCHAR(500) null COMMENT 'seed to generate codes 2fa',
    tw_fa_recovery_codes text null COMMENT 'recovery codes for 2fa'
) COMMENT 'user in application';

CREATE TABLE if not exists smlv.user_health (
    user_id VARCHAR(100) not null,
    height INTEGER not null,
    weight DOUBLE not null,
    register_date DATE not null,
    PRIMARY KEY(user_id, register_date),
    Foreign Key (user_id) REFERENCES user(id)
) COMMENT 'users health data';
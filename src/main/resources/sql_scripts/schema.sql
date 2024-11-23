
create database if not exists SMLV;

CREATE TABLE if not exists SMLV.SMLV_USERS (
    ID VARCHAR(250) NOT NULL PRIMARY KEY COMMENT 'Primary Key',
    ALIAS VARCHAR(75) NOT NULL COMMENT 'user name',
    EMAIL VARCHAR(300) NOT NULL UNIQUE COMMENT 'user credential',
    BIRTH_DATE DATE null COMMENT 'user birthday',
    GENDER CHAR(1) null COMMENT 'users biological gender',
    PASSWORD VARCHAR(200) NOT NULL COMMENT 'user password',
    TWO_FA_SEED VARCHAR(500) null COMMENT 'seed to generate codes 2fa',
    TW_FA_RECOVERY_CODES text null COMMENT 'recovery codes for 2fa',
    INVITATIONS TINYINT DEFAULT 5 COMMENT 'number of invitations remains',
    STATUS_LOGIN VARCHAR(10) default 'PASSWORD' COMMENT 'status login' 
) COMMENT 'user in application';

CREATE TABLE if not exists SMLV.SMLV_HEALTH (
    USER_ID VARCHAR(100) not null,
    HEIGHT INTEGER not null,
    WEIGHT DOUBLE not null,
    REGISTER_DATE DATE not null,
    PRIMARY KEY(USER_ID, REGISTER_DATE),
    Foreign Key (USER_ID) REFERENCES SMLV_USERS(ID)
) COMMENT 'users health data';

-- Example value password is paco1234
insert into SMLV.SMLV_USERS(ID,ALIAS,BIRTH_DATE,GENDER, PASSWORD, EMAIL, TWO_FA_SEED)  values('c20c1add-1281-4317-b5fa-45919fd6bcee','antaler','1998-04-09','M','$2a$12$JMMV8T.vDf/IzPrnp4ISUOwDPS0i97ZL9u4viJ2wTFuN0BmUlL14S', 'dev@antaler.com', 'GI2TCNZRME3TENDEMU2DIYRZMQ4DCMBS');
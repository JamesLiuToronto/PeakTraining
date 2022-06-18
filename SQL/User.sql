CREATE TABLE UserAccount
(
    userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid varchar(200) not null,
    emailAddress varchar(100) NOT NULL unique,
    firstName varchar(100) NOT NULL,
    lastName varchar(100) NOT NULL,
    birthDate Date DEFAULT NULL,
    sex char(1) DEFAULT NULL,
    statusCode varchar(20) NOT NULL,
    createDate datetime DEFAULT current_timestamp(),
    updateDate datetime DEFAULT current_timestamp(),
    createUserId int not null ,
    updateUserId int not null
    )

CREATE TABLE UserLogin
(
    userId INT NOT NULL PRIMARY KEY,
    password varchar(500) null,
    authenticationSource varchar(20) not null,
    locked boolean NULL,
    lastSuccessLogin datetime null,
    lastFaliedLogin datetime null,
    faliedLoginAttemp int NULL,
    createDate datetime DEFAULT current_timestamp(),
    updateDate datetime DEFAULT current_timestamp(),
    createUserId int not null ,
    updateUserId int not null

)

CREATE TABLE UserGroup
(
    userGroupId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid varchar(200) not null,
    groupCode varchar(10) NOT NULL,
    userId int NOT NULL ,
    active bit not null,
    createDate datetime DEFAULT current_timestamp(),
    updateDate datetime DEFAULT current_timestamp(),
    createUserId int not null ,
    updateUserId int not null ,

    CONSTRAINT UserGroup_user FOREIGN KEY (userId)
        REFERENCES UserAccount(userId)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
)

CREATE TABLE AccessControl
(
    accessControlId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    groupCode varchar(20) NOT NULL,
    methodCode varchar(20) NOT NULL,
    allow bit not null,
    note varchar(500) default null,
    utimestamp datetime DEFAULT current_timestamp(),
    index (methodCode)
)

insert into userAccount values (-1, '01010101', 'admin@yahoo.com', 'admin', 'admin', '2000-01-01', 'M', 'ACTIVE',
now(), now(), -1, -1);

insert into userAccount values (1, '01010102', 'jamesliu_8@yahoo.com', 'james', 'admin', '2000-01-01', 'M', 'ACTIVE',
                                now(), now(), -1, -1);

insert into userlogin values (-1, 'admin', 'NULL', null, null, null, 0,  now(), now(), -1, -1);
insert into userlogin values (1, 'james', 'NULL', null, null, null, 0, now(), now(), -1, -1);



insert into UserGroup values (1, UUID(), 'ADMIN', 1, 1, now(), now(), -1, -1);
insert into UserGroup values (2, UUID(), 'USER', 1, 1, now(), now(), -1, -1);
insert into UserGroup values (3, UUID(), 'STUDENT', 1, 1, now(), now(), -1, -1);
insert into UserGroup values (4, UUID(), 'TEACHER', 1, 1, now(), now(), -1, -1);
insert into UserGroup values (5, UUID(), 'ASST', 1, 1, now(), now(), -1, -1);


CREATE TABLE TransactionLog
(
    transactionLogId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid varchar(200) not null,
    transactionTypeCode varchar(20) DEFAULT NULL,
    message text DEFAULT NULL,
    statusCode varchar(20) Default NULL,
    userId int not null ,
    utimestamp datetime DEFAULT current_timestamp()
)



CREATE TABLE AuditLog
(
    auditId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    createDate datetime DEFAULT current_timestamp(),
    updateDate datetime DEFAULT current_timestamp(),
    createUserId int not null ,
    updateUserId int not null
)
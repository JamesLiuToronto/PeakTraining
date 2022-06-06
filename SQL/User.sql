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
    utimestamp datetime DEFAULT current_timestamp(),
    auditId INT NULL
    )

CREATE TABLE UserLogin
(
    userId INT NOT NULL PRIMARY KEY,
    password varchar(50) null,
    authenticationSource varchar(20) not null,
    utimestamp datetime DEFAULT current_timestamp(),
    auditId INT NULL,
    locked boolean NULL
    lastSuccessLogin datetime null,
    lastFaliedLogin datetime null,
    faliedLoginAttemp int NULL

)

CREATE TABLE UserGroup
(
    userGroupId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid varchar(200) not null,
    groupCode varchar(10) NOT NULL,
    userId int NOT NULL ,
    active bit not null,
    utimestamp datetime DEFAULT current_timestamp(),
    auditId INT NULL,

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

insert into AccessControl values (1, 'ADMIN', 'NEW_USER', 1, null, CURDATE());
insert into AccessControl values (2, 'ADMIN', 'UPDATE_USER', 1, null, CURDATE());

insert into UserAccount values ( 1, UUID(), 'jamesliu_8@yahoo.com', 'James', 'Liu', '1970-01-01', 'M', 'ACTIVE', CURDATE() );

insert into UserGroup values (1, UUID(), 'ADMIN', 1, 1, CURDATE())
insert into UserGroup values (2, UUID(), 'USER', 1, 1, CURDATE())
insert into UserGroup values (3, UUID(), 'STUDENT', 1, 1, CURDATE())
insert into UserGroup values (4, UUID(), 'TEACHER', 1, 1, CURDATE())
insert into UserGroup values (5, UUID(), 'ASST', 1, 1, CURDATE())

insert into userlogin values (1, 'james', 'NULL', now(), null )
insert into userlogin values (2, 'test1', 'GOOGLE', now(), null )
insert into userlogin values (3, 'test2', 'FACEBOOK', now(), null )

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
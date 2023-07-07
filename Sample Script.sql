 -- TABLE DROP
DROP TABLE MEMBER;

-- SEQUENCE DROP
DROP SEQUENCE SEQ_USERNO;

CREATE TABLE MEMBER(
    USERNO NUMBER PRIMARY KEY,                      -- 회원번호
    USERID VARCHAR2(15) NOT NULL UNIQUE,            -- 회원아이디
    USERPWD VARCHAR2(15) NOT NULL,                  -- 회원비번
    USERNAME VARCHAR2(20) NOT NULL,                 -- 회원이름
    GENDER CHAR(1) CHECK(GENDER IN ('M', 'F')),     -- 성별
    AGE NUMBER,                                     -- 나이
    EMAIL VARCHAR2(20),                             -- 이메일
    PHONE CHAR(11), -- 작대기없는거                   -- 전화번호
    ADDRESS VARCHAR2(100),                          -- 주소
    HOBBY VARCHAR2(50),                             -- 취미
    ENROLLDATE DATE DEFAULT SYSDATE NOT NULL        -- 회원가입일
);

SELECT * FROM MEMBER;

CREATE SEQUENCE SEQ_USERNO
NOCACHE;

INSERT INTO MEMBER
VALUES(SEQ_USERNO.NEXTVAL, 'admin', '1234', '관리자', 'M', 45, 'admin@iei.or.kr', '01012345555', '서울', null, '2021-07-27');

INSERT INTO MEMBER
VALUES(SEQ_USERNO.NEXTVAL, 'user01', 'pass01', '홍길동', null, 23, 'user01@iei.or.kr', '01022221111', '부산', '등산, 영화보기', '2021-08-27');

COMMIT; -- 커밋안하면 반영안되니 데이터 다 넣고 커밋하기!!




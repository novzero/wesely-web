CREATE SEQUENCE wmember_idx_seq;


CREATE TABLE wmember (
	idx NUMBER PRIMARY KEY,
	userid varchar2(50) NOT NULL,
	password varchar2(200) NOT NULL,
	uuid varchar2(200) NULL,
	username varchar2(50) NOT NULL,
	nickname varchar2(50),
	email varchar2(100),
	phone varchar2(20)
);

CREATE SEQUENCE wmember_roles_idx_seq;

CREATE TABLE wmember_roles(
	idx NUMBER PRIMARY KEY,
	username varchar2(50) NOT NULL,
	role varchar2(50) NOT NULL
);

SELECT * FROM wmember;
SELECT * FROM wmember_roles;


-- 커뮤니티 테이블
CREATE SEQUENCE community_id_seq;
CREATE TABLE community(
	id NUMBER PRIMARY KEY, -- 키필드
	userid varchar2(50) NOT NULL, -- 아이디
	nickname varchar2(100) NOT NULL, -- 작성자
	contents varchar2(300) NOT NULL, -- 글 내용
	regDate timestamp DEFAULT sysdate , -- 작성일
	readCount NUMBER DEFAULT 0 -- 조회수 증가
	);
SELECT * FROM COMMUNITY;
DELETE FROM community WHERE userid = 'wesely';
-- 커뮤니티 댓글 테이블 
CREATE SEQUENCE comm_id_seq;
CREATE TABLE COMM(
	id NUMBER PRIMARY KEY,  -- 키필드
	REF NUMBER NOT NULL, -- 원본글 번호
	name varchar2(100) NOT NULL, -- 작성자
	content varchar2(2000) NOT NULL, -- 내용
	regdate timestamp DEFAULT sysdate -- 작성일
	);
SELECT * FROM comm;

-- 이미지 파일 테이블
CREATE SEQUENCE communityImg_id_seq;
CREATE TABLE communityImg(
	id NUMBER PRIMARY KEY,  -- 키필드
	ref NUMBER NOT NULL, -- 원본글 번호
	uuid varchar2(200) NOT NULL, -- 중복처리위한 키
	fileName varchar2(200) NOT NULL, -- 원본 파일명
	contentType varchar2(200) NOT NULL -- 파일 종류
);
SELECT * FROM communityImg;

SELECT community_id_seq.nextval,community_id_seq.currval  FROM dual;
insert into CommunityImg values (CommunityImg_id_seq.nextval, Community_id_seq.currval,'e2ff4d1b-b89d-423e-9f11-197573a4c515','1.png','image/png');

SELECT * FROM CommunityImg;

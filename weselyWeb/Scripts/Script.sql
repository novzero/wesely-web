CREATE SEQUENCE wmember_idx_seq;


CREATE TABLE wmember (
	idx NUMBER PRIMARY KEY,
	userid varchar2(50) NOT NULL,
	password varchar2(200) NOT NULL,
	uuid varchar2(200) NULL,
	username varchar2(50) NOT NULL,
	nickname varchar2(50),
	authority varchar2(50) NOT NULL,
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
DROP TABLE WMEMBER;
DELETE FROM wmember WHERE USERID = 'wesely2';

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
DROP TABLE COMMUNITY;
SELECT * FROM COMMUNITY;
DELETE FROM community WHERE userid = 'wesely';
-- 커뮤니티 댓글 테이블 
CREATE SEQUENCE comm_id_seq;
CREATE TABLE COMM(
	id NUMBER PRIMARY KEY,  -- 키필드
	REF NUMBER NOT NULL, -- 원본글 번호 
	name varchar2(100) NOT NULL, -- 작성자 이름
	content varchar2(2000) NOT NULL, -- 내용
	regdate timestamp DEFAULT sysdate -- 작성일
	);
SELECT * FROM comm;
DROP TABLE comm;
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
DROP TABLE communityImg;
SELECT community_id_seq.nextval,community_id_seq.currval  FROM dual;
insert into CommunityImg values (CommunityImg_id_seq.nextval, Community_id_seq.currval,'e2ff4d1b-b89d-423e-9f11-197573a4c515','1.png','image/png');

SELECT * FROM CommunityImg;
SELECT * FROM Community;

--===============================================================================

-- ✅ 리뷰가 작성가능한 시설섹션을 만들자
-- 1. 시설섹션에 사용할 시퀀스
CREATE SEQUENCE store_id_seq;

-- 2. 게시판 테이블 생성
CREATE TABLE store(
	id NUMBER PRIMARY KEY,  -- 키필드
	name varchar2(50) NOT NULL, -- 매장명
	address varchar2(100) NOT NULL, -- 매장주소
	opening varchar2(300), -- 매장운영시간
	tel varchar2(30), -- 매장번호
	description varchar2(2000), -- 매장설명
	hashTag varchar2(50)-- 매장해쉬태그
);

-- 3. 리뷰 테이블에 사용할 시퀀스
CREATE SEQUENCE sreview_id_seq;

-- 4. 리뷰 테이블 생성
CREATE TABLE storeReview(
	id NUMBER PRIMARY KEY,  -- 키필드
	REF NUMBER NOT NULL, -- 원본글 번호
	nickname varchar2(50) NOT NULL, -- 유저닉네임
	userProfile varchar2(100) NOT NULL, -- 유저프로필사진
	star NUMBER NOT NULL, -- 별점
	content varchar2(2000) NOT NULL, -- 내용
	regdate timestamp DEFAULT sysdate -- 작성일
);

SELECT * FROM store;
SELECT * FROM sreview_id_seq;




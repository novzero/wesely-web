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

SELECT * FROM store;

INSERT INTO store VALUES (store_id_seq.nextval, '2나다움핏', '대전광역시 중구 대흥동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요12', '#헬스, #에너지넘치는');
INSERT INTO store VALUES (store_id_seq.nextval, '2헬스장이오', '대전광역시 중구 오류동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요2', '#헬스, #에너지넘치는');
INSERT INTO store VALUES (store_id_seq.nextval, '2요가이오', '대전광역시 중구 삼성동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요3', '#헬스, #에너지넘치는');
INSERT INTO store VALUES (store_id_seq.nextval, '2필테이오', '대전광역시 중구 문화동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요4', '#헬스, #에너지넘치는');
INSERT INTO store VALUES (store_id_seq.nextval, '2수영이오', '대전광역시 중구 목동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요5', '#헬스, #에너지넘치는');
INSERT INTO store VALUES (store_id_seq.nextval, '2인라인이오', '대전광역시 중구 괴정동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요6', '#헬스, #에너지넘치는');
INSERT INTO store VALUES (store_id_seq.nextval, '2테니스이오', '대전광역시 중구 용문동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요7', '#헬스, #에너지넘치는');
INSERT INTO store VALUES (store_id_seq.nextval, '2태권도이오', '대전광역시 중구 와동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요8', '#헬스, #에너지넘치는');
INSERT INTO store VALUES (store_id_seq.nextval, '2복싱이오', '대전광역시 중구 탄방동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요9', '#헬스, #에너지넘치는');
INSERT INTO store VALUES (store_id_seq.nextval, '2스케이트이오', '대전광역시 중구 둔산동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요10', '#헬스, #에너지넘치는');
INSERT INTO store VALUES (store_id_seq.nextval, '2만능이오', '대전광역시 중구 오정동 123-123', '월-금 : 9:00-22:00', '042-123-1234', '저희매장 정말 좋아요11', '#헬스, #에너지넘치는');

CREATE SEQUENCE store_id_seq;







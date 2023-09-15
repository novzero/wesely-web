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
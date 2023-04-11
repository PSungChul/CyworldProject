# CyworldProject
## 2주간의 웹 개발 팀 프로젝트 - 싸이월드

### 📌 사라진 Cyworld를 재현 및 재구성
#### 학원에서 2주간 진행된 웹 개발 팀 프로젝트로 프론트엔드 1명 백엔드 4명 총 5명이서 팀을 꾸려 프론트 1명 백엔드 4명으로 지금은 사라진 싸이월드를 재현해 보았습니다.

*

### 📌 CyworldProject ppt 자료
[Cyworld_Project.pptx](https://github.com/PSungChul/CyworldProject/files/11197676/Cyworld_Project.pptx)

#

### 📌 프로젝트내 나의 역할들
#### ✔ 백엔드 총괄 및 병합
#### ✔ ERROR 총괄 및 해결
#### ✔ DB 생성 및 관리
#### ✔ 각종 API 및 SMTP 사용
#### ✔ 로그인 및 회원가입 구현
#### ✔ 메인 - 조회수, 검색, 일촌신청, 일촌평 기능 구현
#### ✔ 프로필 - 메인타이틀, 메인사진, 소개글, 유저 정보 수정 기능 구현
#### ✔ 사진첩, 방명록 - 좋아요 기능 구현

##### 사용된 데이터베이스 : Oracle - xe

##### 사용된 테이블 : SignUp, Views, Ilchon, Ilchonpyeong, BuyMinimi, Diary, Gallery, GalleryComment, GalleryLike, GuestBook, GuestBookLike
	--Oracle

	--시퀀스 테이블
	CREATE SEQUENCE seq_SignUp_idx;

	--유저 테이블
	CREATE TABLE SignUp (
		Idx  NUMBER(3) PRIMARY KEY, --IDX - 기본키, 시퀀스
		Name VARCHAR2(50) NOT NULL, --이름
		UserID VARCHAR2(30), --ID 
		Info VARCHAR2(30), --PW 
		IdentityNum VARCHAR2(20) NOT NULL, --주민번호 
		Gender VARCHAR2(30) NOT NULL, --성별
		Email VARCHAR2(50) NOT NULL, --Email
		PhoneNumber VARCHAR2(30) NOT NULL, --휴대전화
		Address VARCHAR2(255) NOT NULL, --주소
		AddressDetail VARCHAR2(255) NOT NULL, --상세주소
		Platform VARCHAR2(20), --플랫폼
		Minimi VARCHAR2(30), --미니미 
		DotoryNum NUMBER(10), --도토리 개수 
		MainTitle VARCHAR2(100), --메인 타이틀
		MainPhoto VARCHAR2(50), --메인 사진
		MainText VARCHAR2(255), --메인 소개글
		Ilchon NUMBER(38), --일촌 수
		Today NUMBER(38), --일일 조회수
		Total NUMBER(38), --총합 조회수
		ToDate VARCHAR2(20) --방문 날짜
	);

	--조회수 테이블
	CREATE TABLE Views (
		ViewsIdx NUMBER(38), --미니홈피 유저 IDX
		ViewsSession NUMBER(38), --로그인 유저 IDX
		TodayDate VARCHAR2(20) --접속 일자
	);

	--일촌 테이블
	CREATE TABLE Ilchon (
		IlchonIdx NUMBER(38), --미니홈피 유저 IDX
		CONSTRAINT fk_IlchonIdx FOREIGN KEY(IlchonIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		IlchonSession NUMBER(38), --로그인 유저 IDX
		IlchonUp NUMBER(38), --일촌 수
		IlchonName VARCHAR2(100) --일촌 이름
	);

	--일촌평 테이블
	CREATE TABLE Ilchonpyeong (
		Num NUMBER(38) PRIMARY KEY, --일촌평 번호
		IlchonpyeongText VARCHAR2(255), --일촌평 내용
		IlchonpyeongIdx NUMBER(38), --미니홈피 유저 IDX
		CONSTRAINT fk_IlchonpyeongIdx FOREIGN KEY(IlchonpyeongIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		IlchonSession VARCHAR2(50) --로그인 유저 IDX
	);

	--미니미 구매 테이블
	CREATE TABLE BuyMinimi (
		BuyIdx NUMBER(38) PRIMARY KEY, --미니홈피 유저 IDX
		CONSTRAINT fk_BuyIdx FOREIGN KEY(BuyIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		BuyMinimiName VARCHAR2(50) --구매한 미니미 이름
	);

	--다이어리 테이블
	CREATE TABLE Diary (
		DiaryContentRef NUMBER(20) PRIMARY KEY, --다이어리 글 번호
		DiaryContent CLOB, --다이어리 글 내용
		DiaryRegdate DATE, --다이어리 글 작성 일자
		DiaryIdx NUMBER(38), --미니홈피 유저 IDX
		CONSTRAINT fk_DiaryIdx FOREIGN KEY(DiaryIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE --포린키 연결
	);

	--갤러리 테이블
	CREATE TABLE Gallery (
		GalleryContentRef NUMBER(20) PRIMARY KEY, --게시글 번호
		GalleryContent CLOB, --게시글 내용
		GalleryFileName VARCHAR2(50), --게시글 파일 이름
		GalleryRegdate DATE, --게시글 작성 일자
		GalleryIdx NUMBER(38), --미니홈피 유저 IDX
		CONSTRAINT fk_GalleryIdx FOREIGN KEY(GalleryIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		GalleryFileExtension VARCHAR2(20), --게시글 파일 확장자
		GalleryLikeNum NUMBER(38), --게시글 좋아요 수
		GalleryTitle VARCHAR2(50) --게시글 제목
	);

	--갤러리 댓글 테이블
	CREATE TABLE GalleryComment (
		GalleryCommentIdx NUMBER(38), --미니홈피 유저 IDX
		CONSTRAINT fk_GalleryCommentIdx FOREIGN KEY(GalleryCommentIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		GalleryCommentRef NUMBER(38), --게시글 댓글 번호
		GalleryCommentContent VARCHAR2(50), --게시글 댓글 내용
		GalleryCommentRegdate DATE, --게시글 댓글 작성 일자
		GalleryNum NUMBER(38), --게시글 번호
		CONSTRAINT fk_GalleryCommentRef FOREIGN KEY(GalleryNum) REFERENCES Gallery(GalleryContentRef) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		GalleryCommentDeleteCheck NUMBER(38), --게시글 댓글 삭제 여부
		GalleryCommentSession NUMBER(38), --로그인 유저 IDX
		GalleryCommentName VARCHAR2(50) --게시글 댓글 작성자 이름
	);

	--갤러리 좋아요 테이블
	CREATE TABLE GalleryLike (
		GalleryLikeIdx NUMBER(38), --미니홈피 유저 IDX
		CONSTRAINT fk_GalleryLikeIdx FOREIGN KEY(GalleryLikeIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		GalleryLikeRef NUMBER(38), --게시글 번호
		CONSTRAINT fk_GalleryLikeRef FOREIGN KEY(GalleryIdx) REFERENCES Gallery(GalleryContentRef) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		GalleryLikeSession NUMBER(38) --로그인 유저 IDX
	);

	--방명록 테이블
	CREATE TABLE GuestBook (
		GuestBookContentRef NUMBER(5) PRIMARY KEY, --방명록 번호
		GuestBookContent CLOB, --방명록 내용
		GuestBookContentName VARCHAR2(100), --방명록 작성자 이름
		GuestBookRegdate DATE, --방명록 작성 일자
		GuestIdx NUMBER(38), --미니홈피 유저 IDX
		CONSTRAINT fk_GuestIdx FOREIGN KEY(GuestIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		GuestBookLikeNum NUMBER(38), --방명록 좋아요 수
		GuestBookSecretCheck NUMBER(2), --방명록 비밀 여부
		GuestBookSession NUMBER(38), --로그인 유저 IDX
		GuestbookMinimi VARCHAR2(50) --방명록 작성자 미니미
	);

	--방명록 좋아요 테이블
	CREATE TABLE GuestBookLike (
		GuestBookLikeIdx NUMBER(38), --미니홈피 유저 IDX
		CONSTRAINT fk_GuestBookLikeIdx FOREIGN KEY(GuestBookLikeIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		GuestBookLikeRef NUMBER(38), --방명록 번호
		CONSTRAINT fk_GuestBookLikeRef FOREIGN KEY(GuestIdx) REFERENCES GuestBook(GuestBookContentRef) ON DELETE CASCADE ON UPDATE CASCADE, --포린키 연결
		GuestBookLikeSession NUMBER(38) --로그인 유저 IDX
	);
	
##### 사용된 데이터베이스 : MySQL - Cyworld
	CREATE DATABASE Cyworld;
	USE Cyworld;

##### 사용된 테이블 : SignUp, Views, Ilchon, Ilchonpyeong, BuyMinimi, Diary, Gallery, GalleryComment, GalleryLike, GuestBook, GuestBookLike
	#MySQL
	
	#유저 테이블
	CREATE TABLE SignUp (
		Idx  INT(3) PRIMARY KEY AUTO_INCREMENT, #IDX - 기본키, 시퀀스
		Name VARCHAR(50) NOT NULL, #이름
		UserID VARCHAR(30), #ID 
		Info VARCHAR(30), #PW 
		IdentityNum VARCHAR(20) NOT NULL, #주민번호 
		Gender VARCHAR(30) NOT NULL, #성별
		Email VARCHAR(50) NOT NULL, #Email
		PhoneNumber VARCHAR(30) NOT NULL, #휴대전화
		Address VARCHAR(255) NOT NULL, #주소
		AddressDetail VARCHAR(255) NOT NULL, #상세주소
		Platform VARCHAR(20), #플랫폼
		Minimi VARCHAR(30), #미니미 
		DotoryNum INT(10), #도토리 개수 
		MainTitle VARCHAR(100), #메인 타이틀
		MainPhoto VARCHAR(50), #메인 사진
		MainText VARCHAR(255), #메인 소개글
		Ilchon INT(38), #일촌 수
		Today INT(38), #일일 조회수
		Total INT(38), #총합 조회수
		ToDate VARCHAR(20) #접속 일자
	);

	#조회수 테이블
	CREATE TABLE Views (
		ViewsIdx INT(38), #미니홈피 유저 IDX
		ViewsSession INT(38), #로그인 유저 IDX
		TodayDate VARCHAR(20) #접속 일자
	);

	#일촌 테이블
	CREATE TABLE Ilchon (
		IlchonIdx INT(38), #미니홈피 유저 IDX
		CONSTRAINT fk_IlchonIdx FOREIGN KEY(IlchonIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		IlchonSession INT(38), #로그인 유저 IDX
		IlchonUp INT(38), #일촌 수
		IlchonName VARCHAR(100) #일촌 이름
	);


	#일촌평 테이블
	CREATE TABLE Ilchonpyeong (
		Num INT(38) PRIMARY KEY, #일촌평 번호
		IlchonpyeongText VARCHAR(255), #일촌평 내용
		IlchonpyeongIdx INT(38), #미니홈피 유저 IDX
		CONSTRAINT fk_IlchonpyeongIdx FOREIGN KEY(IlchonpyeongIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		IlchonSession VARCHAR(50) #로그인 유저 IDX
	);

	#미니미 구매 테이블
	CREATE TABLE BuyMinimi (
		BuyIdx INT(38), #미니홈피 유저 IDX
		CONSTRAINT fk_BuyIdx FOREIGN KEY(BuyIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		BuyMinimiName VARCHAR(50) #구매한 미니미 이름
	);

	#다이어리 테이블
	Create TABLE Diary (
		DiaryContentRef INT(20) PRIMARY KEY, #다이어리 글 번호
		DiaryContent LONGTEXT, #다이어리 글 내용
		DiaryRegdate DATETIME, #다이어리 글 작성 일자
		DiaryIdx INT(38), #미니홈피 유저 IDX
		CONSTRAINT fk_DiaryIdx FOREIGN KEY(DiaryIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE #포린키 연결
	);

	#갤러리 테이블
	CREATE TABLE Gallery (
		GalleryContentRef INT(20) PRIMARY KEY, #게시글 번호
		GalleryContent LONGTEXT, #게시글 내용
		GalleryFileName VARCHAR(50), #게시글 파일 이름
		GalleryRegdate DATETIME, #게시글 작성 일자
		GalleryIdx INT(38), #미니홈피 유저 IDX
		CONSTRAINT fk_GalleryIdx FOREIGN KEY(GalleryIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		GalleryFileExtension VARCHAR(20), #게시글 파일 확장자
		GalleryLikeNum INT(38), #게시글 좋아요 수
		GalleryTitle VARCHAR(50) #게시글 제목
	);

	#갤러리 댓글 테이블
	CREATE TABLE GalleryComment (
		GalleryCommentIdx INT(38), #미니홈피 유저 IDX
		CONSTRAINT fk_GalleryCommentIdx FOREIGN KEY(GalleryCommentIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		GalleryCommentRef INT(38), #게시글 댓글 번호
		GalleryCommentContent VARCHAR(50), #게시글 댓글 내용
		GalleryCommentRegdate DATETIME, #게시글 댓글 작성 일자
		GalleryNum INT(38), #게시글 번호
		CONSTRAINT fk_GalleryCommentRef FOREIGN KEY(GalleryNum) REFERENCES Gallery(GalleryContentRef) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		GalleryCommentDeleteCheck INT(38), #게시글 댓글 삭제 여부
		GalleryCommentSession INT(38), #로그인 유저 IDX
		GalleryCommentName VARCHAR(50) #게시글 댓글 작성자 이름
	);

	#갤러리 좋아요 테이블
	CREATE TABLE GalleryLike (
		GalleryLikeIdx INT(38), #미니홈피 유저 IDX
		CONSTRAINT fk_GalleryLikeIdx FOREIGN KEY(GalleryLikeIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		GalleryLikeRef INT(38), #게시글 번호
		CONSTRAINT fk_GalleryLikeRef FOREIGN KEY(GalleryIdx) REFERENCES Gallery(GalleryContentRef) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		GalleryLikeSession INT(38) #로그인 유저 IDX
	);

	#방명록 테이블
	CREATE TABLE GuestBook (
		GuestBookContentRef INT(5) PRIMARY KEY, #방명록 번호
		GuestBookContent LONGTEXT, #방명록 내용
		GuestBookContentName VARCHAR(100), #방명록 작성자 이름
		GuestBookRegdate DATETIME, #방명록 작성 일자
		GuestIdx INT(38), #미니홈피 유저 IDX
		CONSTRAINT fk_GuestIdx FOREIGN KEY(GuestIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		GuestBookLikeNum INT(38), #방명록 좋아요 수
		GuestBookSecretCheck INT(2), #방명록 비밀 여부
		GuestBookSession INT(38), #로그인 유저 IDX
		GuestbookMinimi VARCHAR(50) #방명록 작성자 미니미
	);

	#방명록 좋아요 테이블
	CREATE TABLE GuestBookLike (
		GuestBookLikeIdx INT(38), #미니홈피 유저 IDX
		CONSTRAINT fk_GuestBookLikeIdx FOREIGN KEY(GuestBookLikeIdx) REFERENCES SignUp(Idx) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		GuestBookLikeRef INT(38), #방명록 번호
		CONSTRAINT fk_GuestBookLikeRef FOREIGN KEY(GuestIdx) REFERENCES GuestBook(GuestBookContentRef) ON DELETE CASCADE ON UPDATE CASCADE, #포린키 연결
		GuestBookLikeSession INT(38) #로그인 유저 IDX
	);

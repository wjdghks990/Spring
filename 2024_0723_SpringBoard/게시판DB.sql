/*

-- 게시판 일련번호 관리객체
create sequence seq_board_idx


--게시판 DB

create table board
(
	b_idx		int,						-- 일련번호(PK)
	b_subject	varchar2(200) not null,		-- 제목
	b_content	clob		  not null, 	-- 내용
	b_ip		varchar2(100) not null, 	-- 아이피
	b_regdate	date,						-- 작성일자
	b_readhit	int			  default 0,	-- 조회수
	b_use	    char(1)		  default 'y',	-- 사용유무
	mem_idx		int,						-- 작성자 회원번호(FK)
	mem_name	varchar2(100),				-- 작성자명
	b_ref		int,						-- 참조글번호
	b_step		int,						-- 글순서
	b_depth		int							-- 글깊이
)

--기본키 설정
alter table board
	add constraint pk_board_idx primary key(b_idx);
	
--외래키 지정
alter table board
	add constraint fk_board_mem_idx foreign key(mem_idx)
									references member(mem_idx);	

select * from member
select * from board order by b_idx desc,b_step asc

-- sample data

-- 새글쓰기

insert into board values(seq_board_idx.nextval, 
					     '내가 1등...',
					     '내가 첫번째로 등록했네...',
					     '192.168.219.170',
					     sysdate,
					     0,
					     'y',
					     2,
					     '일길동',
					     seq_board_idx.currval,
					     0,
					     0
					     )

-- 답글쓰기

insert into board values(seq_board_idx.nextval, 
					     '아쉽네 내가 1등할 수 있었는데',
					     '다음에는 내가 1등해야지',
					     '192.168.219.61',
					     sysdate,
					     0,
					     'y',
					     6,
					     '배현진',
					     1,
					     1,
					     1
					     )
					     
insert into board values(seq_board_idx.nextval, 
					     '그래 다음에는 니가 1등해봐 ㅋㅋ',
					     '응~아니야 내가 또할거야',
					     '192.168.219.61',
					     sysdate,
					     0,
					     'y',
					     2,
					     '일길동',
					     1,
					     2,
					     2
					     )
					     
delete from board  where b_idx = 17			     

*/
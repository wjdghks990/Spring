/*

-- 일련번호
create sequence seq_comment_tb_idx

create table comment_tb
(
	cmt_idx		int,			-- 일련번호
	cmt_content varchar2(2000),	-- 내용
	cmt_ip		varchar2(200),	-- 아이피
	cmt_regdate date,			-- 등록일자
	b_idx		int,			-- 게시물 일련번호
	mem_idx		int,			-- 회원번호
	mem_name	varchar2(200)	-- 회원명
)

-- 기본키
alter table comment_tb
	add constraint pk_comment_tb_idx primary key(cmt_idx);

-- 외래키
alter table comment_tb
	add constraint fk_comment_tb_b_idx foreign key(b_idx)
									   references board(b_idx);
									   		
alter table comment_tb
	add constraint fk_comment_tb_mem_idx foreign key(mem_idx)
									     references member(mem_idx);	

select * from
(									     
	select
		rank() over(order by cmt_idx desc) as no,
		c.*
	from
	(
		select * from comment_tb where b_idx=21
	) c
)
where no between 1 and 5

select * from comment_tb
	


*/
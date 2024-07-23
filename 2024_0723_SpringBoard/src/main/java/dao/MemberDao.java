package dao;

import java.util.List;

import vo.MemberVo;

public interface MemberDao {
	
	//전체조희
	public abstract List<MemberVo> selectList();
	
	// 다음과 같이 자유롭게 생략 가능하다
	public  MemberVo selectOne(String mem_id);
	
	abstract int insert(MemberVo vo);

    int delete(int mem_idx);

	public abstract int update(MemberVo vo);

	public abstract MemberVo selectOne(int mem_idx);
}

package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.MemberVo;

public class MemberDao {
	
	// DB와 연결
	SqlSession sqlSession; // SqlsessionTemplate interface

	// Setter Injection
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//전체조희
	public List<MemberVo> selectList() {
		
		return sqlSession.selectList("member.member_list");
	}
	
	// mem_id에 해당되는 1건의 정보 얻어온다
	public MemberVo selectOne(String mem_id) {			
		
		return sqlSession.selectOne("member.member_one_id",mem_id);
	}
	
	public int insert(MemberVo vo) {

		
		return sqlSession.insert("member.member_insert",vo);

	}//end:insert()

	public int delete(int mem_idx) {


		return sqlSession.delete("member.member_delete",mem_idx);

	}//end:delete()

	public int update(MemberVo vo) {

		return sqlSession.update("member.member_update",vo);

	}//end:update()

	public MemberVo selectOne(int mem_idx) {	
		return sqlSession.selectOne("member.member_one_idx",mem_idx);
	}
}

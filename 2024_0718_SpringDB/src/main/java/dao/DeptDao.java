package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.DeptVo;

public class DeptDao {
	// SqlSessionTemplate(구현 클래스)의 인터페이스 -> Constructor Injection으로 연결
	// 클래스(설계도)보다 인터페이스(사용설명서)로 제공해야 사용자 입장에서 더 편함
	SqlSession sqlSession;

	// Constructor Injection
	public DeptDao(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}
	
	public List<DeptVo> selectList() {
		//1.SqlSession sqlSession = factory.openSession();
		//2. list = sqlSession.selectList(dept.dept_list);
		//3. sqlSession.close();
		
		return sqlSession.selectList("dept.dept_list");
	}
	
	
}

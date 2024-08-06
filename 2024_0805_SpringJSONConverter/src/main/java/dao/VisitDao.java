package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import vo.VisitVo;

public class VisitDao {
	
	@Autowired
	SqlSession sqlSession; // SqlSessionTemplate
	
	//목록조회
	public List<VisitVo> selectList() {
		// sqlSession.selectList("visit.visit_list");
		// SqlSessionTemplate내부에 재정의된 selectList()가 동작된다
		// 1.openSession()->2.작업수행->3.close()
		/*
		
	class SqlSessionTemplate implements SqlSession {
		
		public List selectList(String mapper_id) {
		  SqlSession sqlSession = factory.openSession();
		  list = sqlSession.selectList(mapper_id);
		  sqlSession.close();
		  return list;
		}
	}	  
		 
		*/	
		return sqlSession.selectList("visit.visit_list");
	} // end - selectList()
	
	public List<VisitVo> selectList(Map<String, Object> map) {		

		
		return sqlSession.selectList("visit.visit_list_condition",map);
	}
	
	public int insert(VisitVo vo) {

		return sqlSession.insert("visit.visit_insert",vo);

	}//end:insert()

	public int delete(int idx) {
		
		return sqlSession.delete("visit.visit_delete",idx);

	}//end:delete()

	// 일부만 조회
	public VisitVo selectOne(int idx) {

		return sqlSession.selectOne("visit.visit_one",idx);
	}

	public int update(VisitVo vo) {

		return sqlSession.update("visit.visit_update",vo);

	}//end:update()

	public int selectRowTotal(Map<String, Object> map) {
		
		return sqlSession.selectOne("visit.visit_row_total",map);
	}

}

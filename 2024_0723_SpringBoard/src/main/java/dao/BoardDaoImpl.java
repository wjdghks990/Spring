package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import vo.BoardVo;

@Repository("board.dao")
public class BoardDaoImpl implements BoardDao {
	
	public BoardDaoImpl() {
		// TODO Auto-generated constructor stub
		System.out.println("--BoardImpl()--");
	}
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<BoardVo> selectList() {
		
		return sqlSession.selectList("board.board_list");
	}

	@Override
	public List<BoardVo> selectList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.board_page_list", map);
	}
	@Override
	public int selectRowTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.board_row_total", map);
	}
	
	@Override
	public int insert(BoardVo vo) {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.board_insert",vo);
	}

	@Override
	public BoardVo selectOne(int b_idx) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.board_one",b_idx);
	}

	@Override
	public int update_readhit(int b_idx) {
		// TODO Auto-generated method stub
		return sqlSession.update("board.board_update_readhit", b_idx);
	}

	@Override
	public int update_step(BoardVo baseVo) {
		// TODO Auto-generated method stub
		return sqlSession.update("board.board_update_step", baseVo);
	}

	@Override
	public int reply(BoardVo vo) {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.board_reply", vo);
	}

	@Override
	public int update_delete(int b_idx) {
		// TODO Auto-generated method stub
		return sqlSession.update("board.board_update_b_use", b_idx);
	}

	@Override
	public int update(BoardVo vo) {
		// TODO Auto-generated method stub
		return sqlSession.update("board.board_update", vo);
	}


	
	
}

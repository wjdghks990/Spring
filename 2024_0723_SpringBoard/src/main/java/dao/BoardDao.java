package dao;

import java.util.List;

import vo.BoardVo;

public interface BoardDao {
	
	List<BoardVo> selectList();

	int insert(BoardVo vo);

	BoardVo selectOne(int b_idx);

	int update_readhit(int b_idx);
}

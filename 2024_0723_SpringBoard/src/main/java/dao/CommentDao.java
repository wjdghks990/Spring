package dao;

import java.util.List;
import java.util.Map;

import vo.CommentVo;

public interface CommentDao {
	
	List<CommentVo> selectList(int b_idx);
	List<CommentVo> selectList(Map<String, Object> map);
	
	int insert(CommentVo vo);
	
	int delete(int cmt_idx);
	int selectRowTotal(int b_idx);

}

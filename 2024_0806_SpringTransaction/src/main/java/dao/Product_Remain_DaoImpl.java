package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.ProductVo;

public class Product_Remain_DaoImpl implements ProductDao {
	
	// 수동 생성이어서 Autowired 사용 안했음
	SqlSession sqlSession;

	// Setter injection
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 재고 CRUD
	@Override
	public List<ProductVo> selectList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("product_remain.product_remain_list");
	}

	@Override
	public int insert(ProductVo vo) {
		// TODO Auto-generated method stub
		return sqlSession.insert("product_remain.product_remain_insert", vo);
	}

	@Override
	public int update(ProductVo vo) {
		// TODO Auto-generated method stub
		return sqlSession.update("product_remain.product_remain_update", vo);
	}	
	
	@Override
	public int delete(int idx) {
		// TODO Auto-generated method stub
		return sqlSession.delete("product_remain.product_remain_delete", idx);
	}
	// 똑같은 상품명이 있는지 검사
	@Override
	public ProductVo selectOne(String name) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("product_remain.product_remain_one_name", name);
	}
	
	@Override
	public ProductVo selectOne(int idx) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("product_remain.product_remain_one_idx", idx);
	}
	
	

}

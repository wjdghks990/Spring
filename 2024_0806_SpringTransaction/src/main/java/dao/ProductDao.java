package dao;

import java.util.List;

import vo.ProductVo;

public interface ProductDao {
	// 기본적인 CRUD
	
	// 전체 상품 조회
	List<ProductVo>		selectList();
	// 상품 1건 조회
	ProductVo			selectOne(int idx);
	// 재고 테이블에 상품이 등록되어져 있는지 (상품명으로)확인 
	// 다른 테이블엔 필요하지 않기 때문에 default로 처리
	default ProductVo	selectOne(String name) { return null; }	
	
	int					insert(ProductVo vo);
	int					update(ProductVo vo);
	int					delete(int idx);
	
}

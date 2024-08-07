package service;

import java.util.List;
import java.util.Map;

import vo.ProductVo;

public interface ProductService {
	
	//입고, 출고, 재고 목록을 싹다 가져오게끔 하기 위함(ArrayList가 Value값)
	//전체 조회
	Map<String, List<ProductVo>> selectTotalMap();
	
	//입고처리
	int insert_in(ProductVo vo) throws Exception;
	//입고취소
	int delete_in(ProductVo vo) throws Exception;
	
	//출고처리
	int insert_out(ProductVo vo) throws Exception;
	//출고취소
	int delete_out(ProductVo vo) throws Exception;


}

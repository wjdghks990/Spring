package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ProductDao;
import vo.ProductVo;

public class ProductServiceImpl implements ProductService {

	ProductDao product_in_dao;		// 입고
	ProductDao product_out_dao;		// 출고
	ProductDao product_remain_dao;	// 재고
	
	public ProductServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	// Constructor Injection (인자의 순서도 중요)
	public ProductServiceImpl(
							  ProductDao product_in_dao,
							  ProductDao product_out_dao, 
							  ProductDao product_remain_dao) {
		super();
		this.product_in_dao = product_in_dao;
		this.product_out_dao = product_out_dao;
		this.product_remain_dao = product_remain_dao;
	}
	
	// 전체 조회 (입고, 출고, 재고)
	@Override
	public Map<String, List<ProductVo>> selectTotalMap() {
		
		List<ProductVo> in_list 	= product_in_dao.selectList(); 		// 입고목록
		List<ProductVo> out_list 	= product_out_dao.selectList(); 	// 출고목록
		List<ProductVo> remain_list = product_remain_dao.selectList(); 	// 재고목록
		
		Map<String, List<ProductVo>> map = new HashMap<String, List<ProductVo>>();
		
		map.put("in_list", in_list);
		map.put("out_list", out_list);
		map.put("remain_list", remain_list);
		
		return map;
	}
	
	// 입고 처리
	@Override
	public int insert_in(ProductVo vo) throws Exception {
		// TODO Auto-generated method stub
		int res = 0;
		
		// 1. 입고 등록하기
		res = product_in_dao.insert(vo);
		
		// 2. 재고 등록(수정)하기
		// 2-1. 재고테이블에 상품이 등록되어있는가?
		ProductVo remainVo = product_remain_dao.selectOne(vo.getName());
		// 2-2. 등록상품이 없으면...
		if(remainVo==null) {
			// 등록 추가
			res = product_remain_dao.insert(vo);
		} 
		// 2-3. 상품이 등록된 상태면... 
		else {
			// 수량 수정
			// 재고 수량 = 기존재고수량 + 추가수량
			int cnt = remainVo.getCnt() + vo.getCnt();
			remainVo.setCnt(cnt); // 재고 수량 초기화
			
			res = product_remain_dao.update(remainVo);
		}
		
		return res; // 0아니면 전부 성공
	}

	// 출고 처리
	@Override
	public int insert_out(ProductVo vo) throws Exception {
		// TODO Auto-generated method stub
		int res = 0;
		
		// 1. 출고 등록하기
		res = product_out_dao.insert(vo);
		
		// 2. 재고 처리(수정)하기
		// 3. 재고테이블에 상품이 등록되어있는가?
		ProductVo remainVo = product_remain_dao.selectOne(vo.getName());
		// 4. 재고 목록에 상품이 없을때 ..
		// -> DB에 들어가기 전에 rollback시켜 취소시키기(트랜잭션)
		if(remainVo==null) {
			throw new Exception("remain_not");	// 예외발생시키기(작업 취소 - rollback)
		} 
		// 5. 재고 목록이 있을때..
		else {
			// 5-1. 출고가 정상 작동할 경우...
			// 재고 수량 	= 기존재고수량 + 추가수량
			int cnt		= remainVo.getCnt() - vo.getCnt();
			// 5-2. 재고수량보다 출고수량이 많은 경우...
			if(cnt < 0) {
				throw new Exception("remain_lack");
			}
			// 재고 수량 수정(정상적인 경우)
			remainVo.setCnt(cnt); 
			res = product_remain_dao.update(remainVo);		
			
		}
		
		return res; // 0아니면 전부 성공
	}

	// 입고 취소
	@Override
	public int delete_in(ProductVo vo) throws Exception {
		//				(int idx)
		int res = 0;
		//0. 취소할 입고상품 정보 얻어오기
		ProductVo inVo = product_in_dao.selectOne(vo.getIdx());
		//		  vo								 (idx)
		//1. 입고상품 삭제
		product_in_dao.delete(inVo.getIdx());
		//					 (idx)
		
		//2. 재고상품 수정(재고상품이 줄어들어야야 함)
		ProductVo remainVo = product_remain_dao.selectOne(inVo.getName());
		//												 (vo.getName())
		int cnt = remainVo.getCnt() - inVo.getCnt();
		//								vo.getCnt();
		// 재고수량보다 입고취소수량이 많은 경우...
		if(cnt < 0) {
			throw new Exception("delete_in_lack");
		}
		
		remainVo.setCnt(cnt); // 재고 수량 초기화
		res = product_remain_dao.update(remainVo);
		
		return res;
	}

	@Override
	public int delete_out(ProductVo vo) throws Exception {
		//				 (int idx)
		int res = 0;
		//0. 취소할 출고상품 정보 얻어오기
		ProductVo outVo = product_out_dao.selectOne(vo.getIdx());	
		//		 								   (idx)
		//1. 출고상품 삭제
		product_out_dao.delete(outVo.getIdx());
		
		//2. 재고상품 수정(재고상품이 늘어나야 함)
		ProductVo remainVo = product_remain_dao.selectOne(outVo.getName());
		int cnt = remainVo.getCnt() + outVo.getCnt();
		remainVo.setCnt(cnt); // 재고 수량 초기화
		res = product_remain_dao.update(remainVo);
		
		return res;
	}
}

package service;

import java.util.List;

import dao.TestDao;

public class TestServiceImpl implements TestService {

	TestDao test_dao;
	
	// Setter injection을 받기 위함
	public void setTest_dao(TestDao test_dao) {
		this.test_dao = test_dao;
	}

	@Override
	public List<String> sido_list() {
		// TODO Auto-generated method stub
		// 딜레이 코드 (로깅 확인 용도)
		try {
			Thread.sleep(1234);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return test_dao.sido_list();
	}

}

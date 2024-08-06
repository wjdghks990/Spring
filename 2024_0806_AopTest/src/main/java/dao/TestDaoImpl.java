package dao;

import java.util.ArrayList;
import java.util.List;

public class TestDaoImpl implements TestDao {

	@Override
	public List<String> sido_list() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		
		list.add("서울");
		list.add("경기");
		list.add("대전");
		list.add("광주");
		list.add("부산");
		
		return list;
	}

}

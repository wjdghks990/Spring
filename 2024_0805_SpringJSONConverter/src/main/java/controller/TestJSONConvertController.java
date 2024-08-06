package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vo.PersonVo;

@Controller
public class TestJSONConvertController {
	
	@RequestMapping("/map_to_json.do")
	@ResponseBody
	public Map map_to_json() {
		
		Map<String, Object> map = new HashMap();
		
		map.put("name", "홍길동");
		map.put("age", 20);
		map.put("addr", "서울 관악구 남부순환로");
		
		return map;
	}
	

	@RequestMapping("/object_to_json.do")
	@ResponseBody
	public PersonVo object_to_json() {
		
		PersonVo p = new PersonVo("홍길동", 20, "서울 동작구 양녕로");
		
		return p;
	}
	
	@RequestMapping("/list_to_json.do")
	@ResponseBody
	public List<String> list_to_json() {
		
		List<String> sido_list = new ArrayList<String>();
		
		sido_list.add("서울");
		sido_list.add("경기");
		sido_list.add("인천");
		sido_list.add("강원");
		sido_list.add("제주");	
		
		return sido_list;
	}
	
	@RequestMapping("/person_list_to_json.do")
	@ResponseBody
	public List<PersonVo> person_list_to_json() {
		
		List<PersonVo> list = new ArrayList<PersonVo>();
		
		list.add(new PersonVo("일길동", 21, "서울 동작구 상도1동"));
		list.add(new PersonVo("이길동", 22, "서울 동작구 상도2동"));
		list.add(new PersonVo("삼길동", 23, "서울 동작구 상도3동"));
		list.add(new PersonVo("사길동", 24, "서울 동작구 상도4동"));
		list.add(new PersonVo("오길동", 25, "서울 동작구 상도5동"));
		
		return list;
	}
	
	@RequestMapping("/person_map_to_json.do")
	@ResponseBody
	public Map<String, Object> person_map_to_json() {
		
		List<PersonVo> list = new ArrayList<PersonVo>();
		
		list.add(new PersonVo("일길동", 21, "서울 동작구 상도1동"));
		list.add(new PersonVo("이길동", 22, "서울 동작구 상도2동"));
		list.add(new PersonVo("삼길동", 23, "서울 동작구 상도3동"));
		list.add(new PersonVo("사길동", 24, "서울 동작구 상도4동"));
		list.add(new PersonVo("오길동", 25, "서울 동작구 상도5동"));
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("size", list.size());
		map.put("search_date", new Date());
		map.put("data", list);
		
		System.out.println(new Date(1722822671941L));
		
		return map;
	}
}
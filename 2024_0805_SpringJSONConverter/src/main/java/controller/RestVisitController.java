package controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.VisitDao;
import vo.VisitVo;

@Controller
public class RestVisitController {
	
	@Autowired
	VisitDao visit_dao;
	
	@Autowired
	HttpServletRequest request;
	
	// 전체조회(visit's'/method = GET)
	//@GetMapping("/visits") // 아래줄과 똑같은 효과
	@RequestMapping(value = "/rest/visits", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectList() { 
		// List의 부가 정보를 같이 넣어서 return하기 위해 반환타입을 Map으로 설정
		
		List<VisitVo> list = visit_dao.selectList();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("size", list.size());
		map.put("search_date", new Date().toString());
		map.put("data", list);
		
		return map;
	}
	
	// 1건 조회
	@RequestMapping(value = "/rest/visit/{idx}", method=RequestMethod.GET)
	@ResponseBody
	public VisitVo selectOne(@PathVariable int idx) { // 경로를 통해 들어온 정보는 확인하는 애너테이션
		
		// VisitVo vo = visit_dao.selectOne(idx);
		
		return visit_dao.selectOne(idx); // return vo;
	}
	
	// 추가 method: POST
	// RequestBody
	// -> { "name":"홍길동", "content":"REST API POST전송한한 데이터", "pwd":"1234" }
	@RequestMapping(value = "/rest/visit", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insert(@RequestBody VisitVo vo) {
		
		// \n -> <br>
		String content = vo.getContent().replaceAll("\n", "<br>");
		vo.setContent(content);
		
		String ip	   = request.getRemoteAddr();
		vo.setIp(ip);
		
		int res        = visit_dao.insert(vo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("result", (res==1)); // (res==1) == true
		
		return map;
		
	}
	
	// 수정 method: PUT
	// RequestBody
	// -> { "idx":10, "name":"홍길동", "content":"REST API POST전송한한 데이터", "pwd":"1234" }
	@RequestMapping(value = "/rest/visit", method=RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> update(@RequestBody VisitVo vo) {
		
		// \n -> <br>
		String content = vo.getContent().replaceAll("\n", "<br>");
		vo.setContent(content);
		
		String ip	   = request.getRemoteAddr();
		vo.setIp(ip);
		
		int res        = visit_dao.update(vo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("result", (res==1)); // (res==1) == true
		
		return map;
		
	}
	
	// 삭제
	@RequestMapping(value = "/rest/visit/{idx}", method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> delete(@PathVariable int idx) {
		
		int res = visit_dao.delete(idx);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", (res==1)); // (res==1) == true
		return map;
	}
	
	// 비밀번호 체크: /rest/visit/idx/{idx}/c-pwd/{c-pwd}
	//			  /rest/visit/idx/10/c-pwd/1234
	@RequestMapping(value = "/rest/visit/idx/{idx}/c-pwd/{c_pwd}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> check_pwd(@PathVariable int idx, @PathVariable String c_pwd) {
		
		VisitVo vo = visit_dao.selectOne(idx);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", vo.getPwd().equals(c_pwd)); 
		
		return map;
	}
	
	// 비밀번호 체크: /rest/visit/idx/{idx}/c-pwd/{c-pwd}
	//			  /rest/visit/idx/10/c-pwd/1234
	@RequestMapping(value = "/rest/visit/checkpwd/{idx}/{c_pwd}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> check_pwd2(@PathVariable int idx, @PathVariable String c_pwd) {
		
		VisitVo vo = visit_dao.selectOne(idx);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", vo.getPwd().equals(c_pwd)); 
		return map;
	}
	
	
}

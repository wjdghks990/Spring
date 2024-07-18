package controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.VisitDao;
import util.MyCommon;
import util.Paging;
import vo.VisitVo;

@Controller
public class VisitController {
	
	public VisitController() {
		// TODO Auto-generated constructor stub
		System.out.println("--VisitConroller()--");
	}
	
	// 인젝션 받기
	@Autowired // (자동엮기)
	VisitDao visit_dao;
	
	@RequestMapping("/visit/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
				
		//1.parameter 받기
		String search	= request.getParameter("search");
		String search_text	= request.getParameter("search_text");
		
		if(search==null) search = "all";
		
		int nowPage = 1;
		try {
			nowPage = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			
		}		
		int start = (nowPage-1) * MyCommon.Visit.BLOCK_LIST +1;
		int end	  = start+MyCommon.Visit.BLOCK_LIST-1;
		
		// 검색 조건을 담을 맵
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("start", start);
		map.put("end", end);
		
		// 이름 + 내용
		if(search.equals("name_content")) {
			
			map.put("name", search_text);		
			map.put("content", search_text);	
			
		}else if(search.equals("name")) {
			// 이름
			map.put("name", search_text);		
		}else if(search.equals("content")) {
			// 내용
			map.put("content", search_text);		
		}
				
		
		int rowTotal = visit_dao.selectRowTotal(map);
		
		// 검색정보 필터
		String search_filter = String.format("search=%s&search_text=%s", search,search_text);
		
		//pageMenu만들기
		String pageMenu = Paging.getPaging("list.do", 					// pageURL
											search_filter,				// 검색필터
											nowPage, 					// 현재페이지
											rowTotal, 					// 전체게시물 수
											MyCommon.Visit.BLOCK_LIST, 	// 한 화면에 보여질 게시물 수
											MyCommon.Visit.BLOCK_PAGE);	// 한 화면에 보여질 페이지 수 
		
		// 방명록 데이터 가져오기
		List<VisitVo> list = visit_dao.selectList(map);
		
		// request binding
		request.setAttribute("list", list);
		request.setAttribute("pageMenu", pageMenu);
		
		return "visit/visit_list"; // /WEB_INF/views/ + viewName + .jsp
	} // end:list()
	
	
	//입력폼 띄우기
	@RequestMapping("/visit/insert_form.do")
	public String insert_form(HttpServletRequest request, HttpServletResponse response) {		
		return "visit_insert_form";
	} // end:insert_form()
	
	//입력(등록)
	@RequestMapping("/visit/insert.do")
	public String insert(HttpServletRequest request, HttpServletResponse response) {	
		// 1.parameter(전달인자) 받기
		String name = request.getParameter("name");
		String content = request.getParameter("content").replaceAll("\n", "<br>");
		String pwd = request.getParameter("pwd");
		
		// 2. ip정보 얻어온다
		String ip = request.getRemoteAddr();
		
		// 3. VisitVo 포장
		// vo에서 생성자 추가하기
		// VisitVo vo = new  VisitVo();
		// vo.set..(); 의 과정 생략
		VisitVo vo = new VisitVo(name, content, pwd, ip);
		
		// 4. DB insert
		int res = visit_dao.insert(vo); // dao클래스에 메서드 추가
				
		// FrontController에게 반환
		return "redirect:list.do";
	} // end:insert()
	
	@RequestMapping(value="/visit/check_pwd.do",
					produces="application/json;charset=utf-8")
	@ResponseBody
	public String check_pwd(HttpServletRequest request, HttpServletResponse response) {
		
		// 1. parameter 받기
		int idx 		= Integer.parseInt(request.getParameter("idx"));
		String c_pwd	= request.getParameter("c_pwd");
		
		// 2. idx에 해당되는 게시물 1건을 얻어온다
		VisitVo vo = visit_dao.selectOne(idx);
		
		// 3. 비밀번호 비교
		boolean bResult = vo.getPwd().equals(c_pwd);		
		
		// JSON Data생성 전송 : {"result":true}
		String json = String.format("{\"result\":%b}", bResult);
		
		return json;
	}
	
	//수정폼 띄우기
	@RequestMapping("/visit/modify_form.do")
	public String modify_form(HttpServletRequest request, HttpServletResponse response) {		
		
		//1. 수정할 게시물의 idx받는다
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		//2. idx에 해당되는 세기물 1건 얻어오기
		VisitVo vo = visit_dao.selectOne(idx);
		
		// textarea \n기능처리 : <br> -> \n
		String content = vo.getContent().replaceAll("<br>", "\n");
		vo.setContent(content);
		
		//3. request Binding
		request.setAttribute("vo", vo);
		
		return "visit_modify_form.jsp";
	} // end:insert_form()
	
	// 수정
	@RequestMapping("/visit/modify.do")
	public String modify(HttpServletRequest request, HttpServletResponse response) {
		
		// 1.parameter(전달인자) 받기
		int    idx  	= Integer.parseInt(request.getParameter("idx"));

		String name 	= request.getParameter("name");
		String content	= request.getParameter("content").replaceAll("\n", "<br>");
		String pwd 		= request.getParameter("pwd");
		
		String page 	= request.getParameter("page");
		String search	= request.getParameter("search");
		String search_text = request.getParameter("search_text");
		
		// 2. ip정보 얻어오기
		String ip 		= request.getRemoteAddr();
		
		// 3. VisitVo 포장
		VisitVo vo = new VisitVo(idx, name, content, pwd, ip);
		
		// 4. DB insert
		int res = visit_dao.update(vo);
		
		
		return "redirect:list.do";
	}
	
	//입력(등록)
		@RequestMapping("/visit/delete.do")
		public String delete(HttpServletRequest request, HttpServletResponse response) {	
			
			// 1. 삭제할 idx 수신
			int idx = Integer.parseInt(request.getParameter("idx"));
			String no = request.getParameter("no"); // 삭제할 글의 순서
			
			// 2. DB delete
			int res = visit_dao.delete(idx); //dao에 delete() 추가
			
			return "redirect:list.do";
		}
	
	
	
	
	
	

	
	
}

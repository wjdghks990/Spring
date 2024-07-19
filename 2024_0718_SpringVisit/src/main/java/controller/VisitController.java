package controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.VisitDao;
import util.MyCommon;
import util.Paging;
import vo.VisitVo;

@Controller
public class VisitController {
	
	// 인젝션 받기
	// @RequestMapping에 의해서 메서드 호출시 DS가 자동으로 인젝션시켜준다
	// 단, 해당 컨트롤러가 Auto-Detecting으로 생성시에만 넣어준다.
	// 수동 생성시에는 생성전에 <context:annotation-config/> 등록해야 한다.
	@Autowired
	HttpServletRequest request;
	
	public VisitController() {
		// TODO Auto-generated constructor stub
		System.out.println("--VisitConroller()--");
	}
	
	// 인젝션 받기
	@Autowired // (자동엮기)
	VisitDao visit_dao;
	
	// (전체+필터)조회
	// /visit/list.do?search=name_content&search_text=포천
	@RequestMapping("/visit/list.do")
	public String list(@RequestParam(name="search",defaultValue = "all") String search, 
						String search_text,
						@RequestParam(name = "page",defaultValue = "1") int nowPage,
						Model model) {
				
		//1.parameter 받기
		/*
		  String search = request.getParameter("search"); 
		  String search_text = request.getParameter("search_text");
		  
		  if(search==null) search = "all";
		  -> 스프링이 파라미터를 대신 받아준다.
		*/
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		/*
		  int nowPage = 1; try { nowPage =
		  Integer.parseInt(request.getParameter("page")); } catch (Exception e) {
		  
		  }
		 */		
		int start = (nowPage-1) * MyCommon.Visit.BLOCK_LIST +1;
		int end	  = start+MyCommon.Visit.BLOCK_LIST-1;
		
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
		//request.setAttribute("list", list);
		//request.setAttribute("pageMenu", pageMenu);
		
		// 결과적으로 request binding
		model.addAttribute("list", list);
		model.addAttribute("pageMenu", pageMenu);
		
		return "visit/visit_list"; // /WEB_INF/views/ + viewName + .jsp
	} // end:list()
	
	
	//입력폼 띄우기
	@RequestMapping("/visit/insert_form.do")
	public String insert_form() {		
		return "visit/visit_insert_form"; // viewName
	} // end:insert_form()
	
	//입력(등록)
	// /visit/insert.do?name=홍길동&content=내용&pwd=1234
	@RequestMapping("/visit/insert.do")
	public String insert(VisitVo vo) {	
		
		// 메서드인자 : DispatcherServlet에 대한 요구사항
		
		// 1.parameter(전달인자) 받기
		/*
		 String name = request.getParameter("name"); 
		 String content = request.getParameter("content").replaceAll("\n", "<br>"); 
		 String pwd = request.getParameter("pwd");
		*/
		
		String content = vo.getContent().replaceAll("\n", "<br>"); 
		vo.setContent(content);
		
		// 2. ip정보 얻어온다
		String ip = request.getRemoteAddr();		
		vo.setIp(ip);
		
		// 3. VisitVo 포장
		// vo에서 생성자 추가하기
		// VisitVo vo = new  VisitVo();
		// vo.set..(); 의 과정 생략
		// VisitVo vo = new VisitVo(name, content, pwd, ip);
		
		// 4. DB insert
		int res = visit_dao.insert(vo); // dao클래스에 메서드 추가
				
		// FrontController에게 반환
		// redirect: 접두어 확인 후 response,sendRedirect("list.do")처리
		return "redirect:list.do";
	} // end:insert()
	
	
	// /visit/check_pwd.do?idx=5&c_pwd=1234
	@RequestMapping(value="/visit/check_pwd.do",
					produces="application/json;charset=utf-8")
	@ResponseBody // 값을 바로 전송할 경우에 사용
	public String check_pwd(int idx, String c_pwd) {
		
		// 1. parameter 받기
		//int idx 		= Integer.parseInt(request.getParameter("idx"));
		//String c_pwd	= request.getParameter("c_pwd");
		
		// 2. idx에 해당되는 게시물 1건을 얻어온다
		VisitVo vo = visit_dao.selectOne(idx);
		
		// 3. 비밀번호 비교
		boolean bResult = vo.getPwd().equals(c_pwd);		
		
		// JSON Data생성 전송 : {"result":true}
		String json = String.format("{\"result\":%b}", bResult);
		
		//@ResponseBody가 붙으면
		//반환값을 DispatcherServlet이 직접 전송시킨다
		//contentType은 @RequestMapping에 produes값을 이용한다.
		/*
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print("{"result":true}");
		*/
		return json;
		// 스프링 전송 방식 1.@ResponseBody 2.포워딩 형식 3.redirect
	}
	
	//수정폼 띄우기
	// /visit/modify_form.do?idx=5
	@RequestMapping("/visit/modify_form.do")
	public String modify_form(int idx, Model model) {		
		
		//1. 수정할 게시물의 idx받는다
		//int idx = Integer.parseInt(request.getParameter("idx"));
		
		//2. idx에 해당되는 세기물 1건 얻어오기
		VisitVo vo = visit_dao.selectOne(idx);
		
		// textarea \n기능처리 : <br> -> \n
		String content = vo.getContent().replaceAll("<br>", "\n");
		vo.setContent(content);
		
		//3. request Binding
		//request.setAttribute("vo", vo);
		model.addAttribute("vo", vo);
		
		return "visit/visit_modify_form";
	} // end:insert_form()
	
	// 수정
	@RequestMapping("/visit/modify.do")
	public String modify(VisitVo vo,
						 @RequestParam(name = "page", defaultValue = "1") int page,
						 @RequestParam(name = "search", defaultValue = "all") String search,
						 String search_text,
						 RedirectAttributes ra) throws Exception {
		
		// 1.parameter(전달인자) 받기
		// vo로 받기
		/*
		  int idx = Integer.parseInt(request.getParameter("idx"));
		  
		  String name = request.getParameter("name"); 
		  String content = request.getParameter("content").replaceAll("\n", "<br>"); 
		  String pwd = request.getParameter("pwd");
		*/
		
		String content = vo.getContent().replaceAll("\n", "<br>");
		vo.setContent(content);
		
		// 따로 받기
		/*
		  String page = request.getParameter("page"); 
		  String search = request.getParameter("search"); 
		  String search_text = request.getParameter("search_text");
		 */
		
		// 2. ip정보 얻어오기
		String ip 		= request.getRemoteAddr();
		vo.setIp(ip);
		
		// 3. VisitVo 포장
		// VisitVo vo = new VisitVo(idx, name, content, pwd, ip);
		
		// 4. DB insert
		int res = visit_dao.update(vo);
		
		// 기존 방법
		//String redirect_page = String.format("redirect:list.do?page=%s&search=%s&search_text=%s", page,search, URLEncoder.endode(search_text,"utf-8"));
		
		//RedirectAttributes : redirect parameter정보담는 객체
		ra.addAttribute("page", page);
		ra.addAttribute("search", search);
		ra.addAttribute("search_text", search_text);
		
		/*
		 	DS이
		 	repose.sendRedirect("list.do?page=1&search=name&search_text=길동")		 
		*/
		return "redirect:list.do";
	}
	
	//입력(등록)
	// /visit/delete.do?idx=5
		@RequestMapping("/visit/delete.do")
		public String delete(int idx) {	
			
			// 1. 삭제할 idx 수신
			//int idx = Integer.parseInt(request.getParameter("idx"));
			
			// 2. DB delete
			int res = visit_dao.delete(idx); //dao에 delete() 추가
			
			return "redirect:list.do";
		}

}

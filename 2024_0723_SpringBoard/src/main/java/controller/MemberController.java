package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.MemberDao;
import vo.MemberVo;

@Controller
@RequestMapping("/member/") // 클래스 레벨에서 매핑처리, 하위 단위에서 처리할 필요 없음
public class MemberController {
	
	// 자동 연결(요청시마다 인젝션)
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
		
	// 처음에 1회 연결
	@Autowired
	MemberDao member_dao; 

	public MemberController() {
		// TODO Auto-generated constructor stub
		System.out.println("--MemberController()--");
	}
	
	@RequestMapping("list.do") 
	public String list(Model model) {
		
		List<MemberVo> list = member_dao.selectList();
		
		model.addAttribute("list", list);
		
		return "member/member_list";
	}
	
	// class RequestMapping + method RequestMapping => /member/login_form.do
	@RequestMapping("login_form.do")
	public String login_form() {
		
		return "member/member_login_form"; // viewName
	}
	
	@RequestMapping("insert_form.do")
	public String inser_form() {
		
		return "member/member_insert_form";
	}
	
	//아이디 중복체크
	// /member/check_id.do?mem_id=one
	@RequestMapping(value = "check_id.do", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String check_id(String mem_id) {
		
		MemberVo vo = member_dao.selectOne(mem_id);
		
		boolean bResult = (vo==null); // vo가 null이면 true(사용가능한 아이디 - 중복되는 데이터가 없다는 뜻)
		
		JSONObject json = new JSONObject();
		json.put("result", bResult);
		
		return json.toString();
	}
	
	@RequestMapping("insert.do")
	public String insert(MemberVo vo) {
		
		String mem_ip = request.getRemoteAddr();
		vo.setMem_ip(mem_ip);
		
		int res = member_dao.insert(vo);
		
		return "redirect:login_form.do";
	}
		
	// /member/login.do?mem_id=one&mem_pwd=1234
	@RequestMapping("login.do")
	public String login(String mem_id,String mem_pwd, RedirectAttributes ra) {
		
		MemberVo user = member_dao.selectOne(mem_id);
		
		// 아이디가 틀린 경우
		if(user==null) {			
			// RedirectAttributes-> redirect시 parameter로 이용된다.
			ra.addAttribute("reason", "fail_id");	
			return "redirect:login_form.do";
			
			//return "redirect:login_form.do?reason=fail_id";
		}
		
		// 비밀번호가 틀린 경우
		if(user.getMem_pwd().equals(mem_pwd)==false) {
			ra.addAttribute("reason", "fail_pwd");	
			ra.addAttribute("mem_id", mem_id);	
									
			return "redirect:login_form.do";
		}
		
		// 
		session.setAttribute("user", user);
		
		return "redirect:../board/list.do";
	}
	
	@RequestMapping("logout.do")
	public String logout() {
		session.removeAttribute("user");
		
		return "redirect:../board/list.do";		
	}
	
	@RequestMapping("delete.do")
	public String delete(int mem_idx, RedirectAttributes ra) {
		
		MemberVo user = (MemberVo) session.getAttribute("user");
		
		if(user.getMem_grade().equals("일반")) {
			session.removeAttribute("user");
		}
		
		if(user.getMem_grade().equals("관리자") && user.getMem_idx() == mem_idx) {
			
			ra.addAttribute("reason", "fail_delete");
			return "redirect:list.do";
		}	
			
			int res = member_dao.delete(mem_idx);
			
			return "redirect:list.do";		
	}
	
	@RequestMapping("modify_form.do")
	public String modify_form(int mem_idx, Model model) {
		
		MemberVo vo = member_dao.selectOne(mem_idx);
		
		model.addAttribute("vo", vo);
		
		return "member/member_modify_form";
	}
	
	@RequestMapping("modify.do")
	public String modify(MemberVo vo) {
		
		String mem_ip = request.getRemoteAddr();
		vo.setMem_ip(mem_ip);
		
		int res = member_dao.update(vo);
		
		MemberVo loginUser = (MemberVo) session.getAttribute("user");
		
		if(loginUser.getMem_idx()==vo.getMem_idx()) {
			
			// 로그인상태정보
			MemberVo user = member_dao.selectOne(vo.getMem_idx());
			
			// session.removeAttribute("user"); 불필요한 작업
			// scope내에 저장방식 Map형식: key / value
			//						  동일한 key로 저장하면 수정처리된다
			session.setAttribute("user", user);
		}
		
		return "redirect:../photo/list.do";
	}
	
	
}

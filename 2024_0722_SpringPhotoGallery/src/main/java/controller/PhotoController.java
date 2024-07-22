package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.PhotoDao;
import util.MyCommon;
import util.Paging;
import vo.MemberVo;
import vo.PhotoVo;

@Controller
@RequestMapping("/photo/")
public class PhotoController {
	
	@Autowired
	PhotoDao photo_dao;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ServletContext application;
	
	public PhotoController() {
		// TODO Auto-generated constructor stub
		System.out.println("--PhotoController()--");
	}
	
	// /photo/list.do
	// /photo/list.do?page=2
	@RequestMapping("list.do")
	public String list(@RequestParam(name = "page",defaultValue = "1") int nowPage,
					   Model model) {
		
		// 게시물의 범위 계산(start/end)
		int start = (nowPage-1) * MyCommon.Photo.BLOCK_LIST + 1;
		int end   = start + MyCommon.Photo.BLOCK_LIST - 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		
		List<PhotoVo> list = photo_dao.selectList(map);
		
		// 전체 게시물 수
		int rowTotal = photo_dao.selectRowTotal();
		
		//pageMenu만들기
		String pageMenu = Paging.getPaging("list.do", 					// pageURL
											nowPage, 					// 현재페이지
											rowTotal, 					// 전체게시물 수
											MyCommon.Photo.BLOCK_LIST, 	// 한 화면에 보여질 게시물 수
											MyCommon.Photo.BLOCK_PAGE);	// 한 화면에 보여질 페이지 수
		
		// request binding
		model.addAttribute("list", list);
		model.addAttribute("pageMenu", pageMenu);
				
		return "photo/photo_list";
	}
	
	// 사진 등록폼 띄우기
	@RequestMapping("insert_form.do")
	public String insert_form() {
		
		return "photo/photo_insert_form";
	}
	
	// 사진 등록
	// photo/insert.do?p_title=제목&p_content=내용&photo=a.png
	//							요청 parameter이름과 받는 변수명이 동일하면 
	//							@RequestParam(name="")의 name속성은 생략가능
	@RequestMapping("insert.do")
	public String insert(PhotoVo vo, @RequestParam MultipartFile photo,
						 RedirectAttributes ra) throws Exception {
		
		// 유저 정보 얻어오기
		MemberVo user = (MemberVo) session.getAttribute("user");
		
		// session timeout
		if(user==null) {
			ra.addAttribute("reason", "session_timeout");
			
			return "redirect:../member/login_form.do";
		}
		
		// 파일업로드
		String absPath = application.getRealPath("/resources/images/");
		
		String p_filename = "no_file";
		if(!photo.isEmpty()) {
			// 업로드된 파일 이름 얻어오기
			p_filename = photo.getOriginalFilename();
			
			File f = new File(absPath,p_filename);
			
			if(f.exists()) {
				//저장경로에 동일한 파일이 존재하면=>다른이름으로 화일명 변경
				//변경파일명 = 시간_원래파일명
				long tm = System.currentTimeMillis();
				p_filename = String.format("%d_%s",tm,p_filename);
				
				f = new File(absPath,p_filename);				
			}
			
			// 임시파일=>내가 지정한 위치로 복사
			photo.transferTo(f);
		}
		//업로드된 파일이름 얻어오기
		vo.setP_filename(p_filename);
		
		// IP
		String p_ip = request.getRemoteAddr();
		vo.setP_ip(p_ip);
		
		String p_content = vo.getP_content().replaceAll("\n", "<br>");
		vo.setP_content(p_content);
		
		//로그인 유저넣는다.
		vo.setMem_idx(user.getMem_idx());
		vo.setMem_name(user.getMem_name());
		
		// DB insert
		int res = photo_dao.insert(vo);
				
		return "redirect:list.do";
	}
	
	// /photo/photo_one.do?p_idx=5
	@RequestMapping(value = "photo_one.do",
					produces = "application/json;charset=utf-8;")
	@ResponseBody // 현재 반환값을 응답데이터를 이용해라
	public String photo_one(int p_idx) {
		
		PhotoVo vo = photo_dao.selectOne(p_idx);
		
		// VO -> JSON 객체 생성(필요없는건 안넘겨도 됨)
		JSONObject json = new JSONObject();
		json.put("p_idx", 		vo.getP_idx());
		json.put("p_title",		vo.getP_title());
		json.put("p_content", 	vo.getP_content());
		json.put("p_filename", 	vo.getP_filename());
		json.put("p_regdate",	vo.getP_regdate());
		json.put("p_ip", 		vo.getP_ip());
		json.put("mem_idx", 	vo.getMem_idx());
		json.put("mem_name", 	vo.getMem_name());
		
		return json.toString();
	}
	
	// /photo/delete.do?p_idx=5
	@RequestMapping("delete.do")
	public String delete(int p_idx) {
		
		PhotoVo vo = photo_dao.selectOne(p_idx);
		
		// /images/의 절대경로
		String absPath = request.getServletContext().getRealPath("/resources/images/");
		//						(절대경로)		(삭제)파일명	
		File delFile = new File(absPath, vo.getP_filename());		
		delFile.delete();
		
		int res = photo_dao.delete(p_idx);
		
		return "redirect:list.do";
	}
	
	@RequestMapping("modify_form.do")
	public String modify_form(int p_idx, Model model) {
		
		PhotoVo vo = photo_dao.selectOne(p_idx);
		
		String p_content = vo.getP_content().replaceAll("<br>", "\n");
		vo.setP_content(p_content);
		
		model.addAttribute("vo", vo);
		
		return "photo/photo_modify_form";
	}
	
	@RequestMapping("modify.do")
	public String modify(PhotoVo vo) {
		
		vo.getP_idx();
		vo.getP_title();
		vo.getP_content();
		
		int res = photo_dao.update(vo);
		
		return "redirect:list.do";
	}
	
	@RequestMapping(value = "photo_upload.do", 
					produces = "application/json; charset=utf-8;")
	@ResponseBody
	public String photo_upload(int p_idx, 
						 @RequestParam MultipartFile photo) throws Exception {
		
		
		
		String absPath = application.getRealPath("/resources/images/");
		
		// 업로드화일명을 얻어온다
		String p_filename = "";		
		if(!photo.isEmpty()) {
			// 업로드된 파일 이름 얻어오기
			p_filename = photo.getOriginalFilename();
			
			File f = new File(absPath,p_filename);
			
			if(f.exists()) {
				//저장경로에 동일한 파일이 존재하면=>다른이름으로 화일명 변경
				//변경파일명 = 시간_원래파일명
				long tm = System.currentTimeMillis();
				p_filename = String.format("%d_%s",tm,p_filename);
				
				f = new File(absPath,p_filename);				
			}
			
			// 임시파일=>내가 지정한 위치로 복사
			photo.transferTo(f);
		}
		
		// p_idx에 저장된 이전파일은 삭제
		PhotoVo vo = photo_dao.selectOne(p_idx);
		File delFile = new File(absPath, vo.getP_filename());
		delFile.delete();
		
		//update된 파일이름 수정
		vo.setP_filename(p_filename);
		int res = photo_dao.update_filename(vo);
		
		// 변경화일명 JSON형식으로 반환
		//{"p_filename":"a.jpg"}
		JSONObject json = new JSONObject();
		json.put("p_filename", p_filename);
		
		return json.toString();
	}
	
}

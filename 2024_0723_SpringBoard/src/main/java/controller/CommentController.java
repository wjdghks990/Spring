package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.CommentDao;
import util.MyCommon;
import util.Paging;
import vo.CommentVo;

@Controller
@RequestMapping("/comment/")
public class CommentController {
	
	@Autowired
	CommentDao comment_dao;
	
	@Autowired
	HttpServletRequest request;
	
	public CommentController() {
		// TODO Auto-generated constructor stub
		System.out.println("--CommentController()--");
	}
	
	//	/comment/list.do?b_idx=5&page=1
	@RequestMapping("list.do")
	public String list(int b_idx, 
					   @RequestParam(name = "page", defaultValue = "1") int nowPage,
					   Model model) {
		
		// 가져올 범위 계산
		int start = (nowPage-1) * MyCommon.Comment.BLOCK_LIST+1;
		int end   = start + MyCommon.Comment.BLOCK_LIST-1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("b_idx", b_idx);
		map.put("start", start);
		map.put("end"  , end);
		
		
		List<CommentVo> list = comment_dao.selectList(map);
		
		// Paging Menu 만들기
		// b_idx의 댓글 개수 구한다
		int rowTotal = comment_dao.selectRowTotal(b_idx);
		
		String pageMenu = Paging.getCommentPaging(nowPage, 
												  rowTotal, 
												  MyCommon.Comment.BLOCK_LIST, 
												  MyCommon.Comment.BLOCK_PAGE);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMenu", pageMenu);
		
		return "comment/comment_list";
	}
	
	// /comment/insert.do?cmt_content=내용&b_idx=5&mem_idx=2&mem_name=일길동
	@RequestMapping(value = "insert.do", produces = "application/json; charset=utf-8;")
	@ResponseBody
	public String insert(CommentVo vo) {
		
		String cmt_ip = request.getRemoteAddr();
		vo.setCmt_ip(cmt_ip);
		
		String cmt_content = vo.getCmt_content().replaceAll("\n", "<br>");
		vo.setCmt_content(cmt_content);
		
		int res = comment_dao.insert(vo); // 들어간 행수
		
		JSONObject json = new JSONObject();
		json.put("result", res==1); // 제대로 insert가 되었느냐 // {"result" : true} or {"result" : false}
		
		return json.toString();
	}
	
	// /comment/delete.do?cmt_idx=5
	@RequestMapping(value = "delete.do", produces = "application/json; charset=utf-8;")
	@ResponseBody
	public String delete(int cmt_idx) {
		
		int res = comment_dao.delete(cmt_idx); // 들어간 행수
		
		JSONObject json = new JSONObject();
		json.put("result", res==1); // 제대로 delete가 되었느냐 // {"result" : true} or {"result" : false}
		
		return json.toString();
		
	}
}

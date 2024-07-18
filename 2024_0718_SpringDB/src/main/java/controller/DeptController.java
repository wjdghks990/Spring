package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.DeptDao;
import vo.DeptVo;

@Controller
public class DeptController {

	DeptDao dept_dao;

	public DeptController(DeptDao dept_dao) {
		super();
		this.dept_dao = dept_dao;
	}
	
	@RequestMapping("/dept/list.do")
	public String list(Model model) {
		
		List<DeptVo> list = dept_dao.selectList(); // 미리 세팅되어져 있기 때문에 list에 넣기만 하면(사용만 하면)된다.
		//System.out.println(list.size());
		
		model.addAttribute("list", list);
		
		return "dept/dept_list"; // <- viewName
		// /WEB-INF/views/ + viewName + .jsp;
		// /WEB-INF/views/ + dept/dept_list + .jsp;
	}
	
	
}

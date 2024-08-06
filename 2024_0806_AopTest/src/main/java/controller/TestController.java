package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import service.TestService;

@Controller
public class TestController {
	
	@Autowired
	TestService test_service;
	
	@RequestMapping("/") // 실행만 해도 요청됨
	public String sido_list(Model model) {
		
		List<String> list = test_service.sido_list(); // test_service는 dao에게 요청함.
		
		model.addAttribute("list", list);
		
		return "test";
	}
	
	
}

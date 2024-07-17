package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TestController {
	
	public TestController() {
		// TODO Auto-generated constructor stub
		System.out.println("--TestController()--");
	}
	
	// dispatcher servlet이 호출해준다.
	@RequestMapping("/test.do")
	public String test() {
					   // View Resolver
		return "test"; // viewName	: /WEB-INF/views/+ viewName + .jsp
	}
	
	@RequestMapping(value = "/hello.do", produces = "text/html;charset=utf-8")
	@ResponseBody // return 값이 즉시 응답 처리
	public String hello() {
	
		return "hello : 안녕하세요";
	}
	
	@RequestMapping("/hi.do")
	public String hi(Model model) {
		
		String msg = "Hi~에이치아이~";
		
		// model통해서 전달된 데이터는
		// DispatcherServlet(메인 컨트롤러)에 전달
		// => DS는 request binding or parameter사용
		// return값이 뷰 			: request binding
		// return값이 redirect면  : parameter 사용 
		model.addAttribute("msg",msg);
		
		return "hi";
	}
	
	@RequestMapping("/bye.do")
	public ModelAndView bye() {
		
		String msg = "GoodBye!!";
		
		ModelAndView mv = new ModelAndView();
		
		//DS는 전달된 데이터를 request binding
		mv.addObject("msg", msg);
		
		//DS는 전달된 뷰정보 완성시키기 위해서 ViewResolver에 작업지시
		mv.setViewName("bye"); // /WEB-INF/views/ + bye + .jsp
		
		return mv;
	}
	
	@RequestMapping("/hi2.do")
	public String hi2(RedirectAttributes ra) {
		
		String name = "Tom";

		ra.addAttribute("name",name);
		
		return "redirect:test.do";
	}
	
}

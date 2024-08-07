package controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import service.ProductService;
import vo.ProductVo;

@Controller
public class ProductController {
	
	@Autowired
	ProductService product_service;
	
	@RequestMapping("/product/list.do")
	public String list(Model model) {		
		
		Map<String, List<ProductVo>> map = product_service.selectTotalMap();
		
		model.addAttribute("map", map);
		
		return "product/product_list";
	}
	
	// /product/insert_in.do?name=TV&cnt=100
	@RequestMapping("/product/insert_in.do")
	public String insert_in(ProductVo vo) {
		
		// 필수로 예외처리 해줘야함! 컨트롤러에 떠넘기면 에러만 떨어짐
		try {
			product_service.insert_in(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return "redirect:list.do";
	}
	
	// /product/insert_out.do?name=TV&cnt=100
	@RequestMapping("/product/insert_out.do")
	public String insert_out(ProductVo vo, RedirectAttributes ra) {
		
		// 필수로 예외처리 해줘야함! 컨트롤러에 떠넘기면 에러만 떨어짐
		try {
			product_service.insert_out(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			String message = e.getMessage();
			ra.addAttribute("error", message);
		}
		// 				 list.do?reason=remain_not
		return "redirect:list.do";
	}
	
	// /product/delete_in.do?idx=5
	@RequestMapping("/product/delete_in.do")
	public String delete_in(ProductVo vo, RedirectAttributes ra) {
		
		// 필수로 예외처리 해줘야함! 컨트롤러에 떠넘기면 에러만 떨어짐
		try {
			product_service.delete_in(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			ra.addAttribute("error", e.getMessage());
		}
		
		return "redirect:list.do";
	}

	// /product/delete_out.do?idx=5
	@RequestMapping("/product/delete_out.do")
	public String delete_out(ProductVo vo, RedirectAttributes ra) {
		
		// 필수로 예외처리 해줘야함! 컨트롤러에 떠넘기면 에러만 떨어짐
		try {
			product_service.delete_out(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			ra.addAttribute("error", e.getMessage());
		}
		
		return "redirect:list.do";
	}
}

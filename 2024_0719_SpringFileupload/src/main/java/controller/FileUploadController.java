package controller;



import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vo.PhotoVo;

@Controller
public class FileUploadController {
	
	// DispatcherServlet Injection(주입) 시켜준다.
	
	@Autowired // 인젝션 받기
	ServletContext application; // 절대 경로를 찾아내줘.
	
	// /upload1.do?title=제목&photo=a.jpg
	// @RequestParam(name = "photo") MultipartFile photo <= 이름이 동일하면 name="photo" 생략 가능
	@RequestMapping("/upload1.do")
	public String upload1(String title, @RequestParam(name = "photo") 
	MultipartFile photo, Model model) throws Exception {
																	
		// 웹(상대)경로
		String webPath = "/resources/images/";
		// 상대경로를 이용해서 절대 경로 구하기
		String absPath = application.getRealPath(webPath);
		System.out.println(absPath);
		
		String filename= "no_file";
		
		// 사진이 비어있지 않으면...
		if(!photo.isEmpty()) {
			
			// 업로드된 파일명을 구합니다.
			filename = photo.getOriginalFilename();
			
			File f = new File(absPath,filename);
			
			if(f.exists()) { //동일파일이 존재하니?
				
				long tm = System.currentTimeMillis();
				filename = String.format("%d_%s", tm,filename); // 파일명을 달리 함(시간을 추가)
				
				f = new File(absPath,filename); // 새로운 파일로 등록
			}
			
			// Spring이 저장해놓은 임시파일을 복사한다
			photo.transferTo(f);			
		}
		
		// 결과적으로 request binding
		model.addAttribute("title", title);
		model.addAttribute("filename", filename);
		
		return "result1"; // viewName
	} // end - upload2()
	
	// /upload2.do?title=제목&photo=a.jpg
	// @RequestParam(name = "photo") MultipartFile photo <= 이름이 동일하면 name="photo" 생략 가능
	@RequestMapping("/upload2.do")
	public String upload2(PhotoVo vo, Model model) throws Exception { // 객체로 받기 위함
		
		// 웹(상대)경로
		String webPath = "/resources/images/";
		// 상대경로를 이용해서 절대 경로 구하기
		String absPath = application.getRealPath(webPath);
		System.out.println(absPath);
		
		String filename= "no_file";
		
		MultipartFile photo = vo.getPhoto();
		
		// 사진이 비어있지 않으면...
		if(!photo.isEmpty()) {
			
			// 업로드된 파일명을 구합니다.
			filename = photo.getOriginalFilename();
			
			File f = new File(absPath,filename);
			
			if(f.exists()) { //동일파일이 존재하니?
				
				long tm = System.currentTimeMillis();
				filename = String.format("%d_%s", tm,filename); // 파일명을 달리 함(시간을 추가)
				
				f = new File(absPath,filename); // 새로운 파일로 등록
			}
			
			// Spring이 저장해놓은 임시파일을 복사한다
			photo.transferTo(f);			
		}
		vo.setFilename(filename);
		
		model.addAttribute("vo", vo);
		
		return "result2"; // viewName
	}
}



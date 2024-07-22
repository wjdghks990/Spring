package controller;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	// parameter이름이 동일하면 배열 또는 List로 받는다
	// /upload3.do?title=제목&photo=a.jpg&photo=b.jpg  <- 실제 파라미터가 이렇게 넘어오진 않는다.
	@RequestMapping("/upload3.do")
	public String upload3(String title,
						  @RequestParam(name="photo") MultipartFile [] photo_array,
						  Model model) throws Exception {		
		// 웹(상대)경로
		String webPath = "/resources/images/";
		// 상대경로를 이용해서 절대 경로 구하기
		String absPath = application.getRealPath(webPath);
		System.out.println(absPath);
		
		String filename1= "no_file";
		String filename2= "no_file";
		
		for(int i=0; i<photo_array.length; i++) {
			
			MultipartFile photo = photo_array[i]; // 그냥 변수명 "photo"이다. url의 photo와는 아무 관련 없음.
			
			// 사진이 비어있지 않으면...
			if(!photo.isEmpty()) {
				
				// 업로드된 파일명을 구합니다.
				String filename = photo.getOriginalFilename(); // 지역변수이다.
				
				File f = new File(absPath,filename);
				
				if(f.exists()) { //동일파일이 존재하니?
					
					long tm = System.currentTimeMillis();
					filename = String.format("%d_%s", tm,filename); // 파일명을 달리 함(시간을 추가)
					
					f = new File(absPath,filename); // 새로운 파일로 등록
				}
				
				// Spring이 저장해놓은 임시파일을 복사한다
				photo.transferTo(f);	
				
				if(i==0)
					filename1 = filename;
				else
					filename2 = filename;
			}
		}// end-for
		
		// model통해서 request binding
		// title 하나의 두 개의 파일이 들어가는 구조
		model.addAttribute("title", title);
		model.addAttribute("filename1", filename1);
		model.addAttribute("filename2", filename2);
		
		return "result3"; // viewName
	}
	
	// parameter이름이 동일하면 배열 또는 List로 받는다
	// /upload4.do?title=제목&photo=a.jpg&photo=b.jpg&...  <- 실제 파라미터가 이렇게 넘어오진 않는다.
	@RequestMapping("/upload4.do")
	public String upload4(String title,
						  @RequestParam(name = "photo") List<MultipartFile> photo_list,
						  Model model) throws Exception {
		
		List<String> filename_list = new ArrayList<String>(); // 파일의 개수가 불규칙적이기 때문에 ArrayList로 받기, filename1, filename2.. 이런식의 String으로는 받을 수 없다.
		
		// 웹(상대)경로
		String webPath = "/resources/images/";
		// 상대경로를 이용해서 절대 경로 구하기
		String absPath = application.getRealPath(webPath);
		System.out.println(absPath);
		
		for(MultipartFile photo: photo_list) { 
			// 사진이 비어있지 않으면...
			if(!photo.isEmpty()) {
				
				// 업로드된 파일명을 구합니다.
				String filename = photo.getOriginalFilename(); // 지역변수이다.
				
				File f = new File(absPath,filename);
				
				if(f.exists()) { //동일파일이 존재하니?
					
					long tm = System.currentTimeMillis();
					filename = String.format("%d_%s", tm,filename); // 파일명을 달리 함(시간을 추가)
					
					f = new File(absPath,filename); // 새로운 파일로 등록
				}
				
				// Spring이 저장해놓은 임시파일을 복사한다
				photo.transferTo(f);	
				
				filename_list.add(filename);
			}	
		} // end - for
		
		// request binding
		model.addAttribute("title", title);
		model.addAttribute("filename_list", filename_list);
		
		return "result4";
	}
}



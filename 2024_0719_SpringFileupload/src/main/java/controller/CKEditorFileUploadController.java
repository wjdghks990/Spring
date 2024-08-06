package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class CKEditorFileUploadController {

	
	@Autowired
	ServletContext application;
	
	
	@RequestMapping(value="/ckeditorImageUpload.do", method=RequestMethod.POST)
	@ResponseBody
	public String ckeditorImageUpload(
			HttpServletRequest request, 
			@RequestParam MultipartFile upload) throws     Exception {
			

        try{
 
            String fileName = upload.getOriginalFilename(); 
            
            //여러분들의 저장경로 설정
            String web_path = "/resources/upload/";
            
            
            String abs_path = application.getRealPath(web_path);
            //String uploadPath = "저장경로/" + fileName;//저장경로
            File f = new File(abs_path,fileName);
            //동일화일이 있는경우
            if(f.exists()){
            	 long time = System.currentTimeMillis();
            	 int index = fileName.lastIndexOf('.');
            	 String f_name = fileName.substring(0,index);
            	 String f_ext  = fileName.substring(index);
            	 
            	 fileName = String.format("%s_%d%s", f_name,time,f_ext);
            	 f = new File(abs_path,fileName);
            }
            //업로드화일 지정된 위치로 복사
            upload.transferTo(f);
 
            
            String url = request.getRequestURL().toString().replaceAll("/ckeditorImageUpload.do", "");
            //System.out.println(url);
            
            String fileUrl = url + web_path + fileName;//url경로
            
            // 서버로 파일 전송 후 이미지 정보 확인을 위해 filename, uploaded, fileUrl 정보를 
            // JSON 형식으로 응답해야함
            // {"filename": fileName , "uploaded": 1, "url" : fileUrl }
            
            JSONObject json = new JSONObject();
            json.put("filename", fileName);
            json.put("uploaded", 1);
            json.put("url", fileUrl);
            
            return json.toString();
            
            
 
        }catch(IOException e){
            e.printStackTrace();
        }
       
        return "";

	}
	

	
}
package vo;

import org.springframework.web.multipart.MultipartFile;

// 객체로 받기 위해 Vo생성
public class PhotoVo {
	
	String title;
	String filename;
	
	MultipartFile photo;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public MultipartFile getPhoto() {
		return photo;
	}
	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
}

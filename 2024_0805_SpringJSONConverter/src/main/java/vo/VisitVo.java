package vo;

public class VisitVo {
	
	int		idx;
	int		no; // 조회되었을 때 글 순서
	String 	name;
	String	content;
	String	pwd;
	String	ip;
	String	regdate;
	
	public VisitVo() {
		
	}	
	
	// 수정시 필요
	public VisitVo(int idx, String name, String content, String pwd, String ip) {
		super();
		this.idx = idx;
		this.name = name;
		this.content = content;
		this.pwd = pwd;
		this.ip = ip;
	}

	// 추가시 필요
	public VisitVo(String name, String content, String pwd, String ip) {
		super();
		this.name = name;
		this.content = content;
		this.pwd = pwd;
		this.ip = ip;
	}
	public int getNo() {
		return no;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
}


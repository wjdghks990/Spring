package vo;

public class PersonVo {
	
	String	name;
	int		age;
	String	addr;
	
	// 다른 생성자가 있을 시에는 무조건 기본생성자 만들기! (하나도 없을 땐 자체적으로 만들어줌)
	public PersonVo() {
		// TODO Auto-generated constructor stub
	}	
	
	public PersonVo(String name, int age, String addr) {
		super();
		this.name = name;
		this.age = age;
		this.addr = addr;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
}

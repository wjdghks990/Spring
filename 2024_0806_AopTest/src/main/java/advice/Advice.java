package advice;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;
// DS의 요청을 받아서 메서드를 호출
public class Advice {
	
	// injection
	@Autowired
	HttpServletRequest request;
	
	// JoinPoint : PointCut의 정보를 담는 객체
	public void before(JoinPoint jp){
		Signature s =  jp.getSignature();
		
		long start = System.currentTimeMillis();
		
		request.setAttribute("start", start);
		
		// 클래스 + 메서드
		System.out.println("----before:" + s.toShortString());
	}
	
	public void after(JoinPoint jp){
		Signature s =  jp.getSignature();
		
		// 오토박싱
		Long start = (Long)request.getAttribute("start");
		
		long end = System.currentTimeMillis();
						
		// 전체 패키지 경로 + 클래스 + 메서드
		System.out.println("----after:" + s.toLongString());
		
		System.out.printf("수행시간 : %d(ms)\n", end-start);
		
	}
	// toString()만 써도 적당히 정보를 알아낼 수 있다.
}

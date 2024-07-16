<%@page import="vo.PersonVo"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// 사용자 입장

	// Spring이 사용하고 있는 bean에 저장소를 관리하는 객체 정보 얻어온다
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
	
	// Spring생성한 Bean정보를 얻어오기 (2가지 방식 중 선택 - 똑같은 결과)
	PersonVo p1 = (PersonVo)wac.getBean("p1");
	PersonVo p2 = wac.getBean("p2", PersonVo.class);
	
	// EL로 값을 표현하기 위함
	pageContext.setAttribute("p1", p1);
	pageContext.setAttribute("p2", p2);
	
%>  

<hr>
	p1's info
<hr>  
이름 : ${ p1.name } <br>
나이 : ${ p1.age } <br>
주소 : ${ p1.addr } 

<hr>
	p2's info
<hr>  
이름 : ${ p2.name } <br>
나이 : ${ p2.age } <br>
주소 : ${ p2.addr }   
    

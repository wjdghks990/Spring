<%@page import="vo.PersonVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	// Java Code
	// 1. Constructor을 이용해서 초기화시키는 방법
	PersonVo p1 = new PersonVo("일길동",20,"서울시 관악구 남부순환로 111");	
	
	// 2. Setter를 이용해서 값을 넣는 방법
	PersonVo p2 = new PersonVo();
	p2.setName("이길동");
	p2.setAge(30);
	p2.setAddr("서울시 관악구 남부순환로 222");
	
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

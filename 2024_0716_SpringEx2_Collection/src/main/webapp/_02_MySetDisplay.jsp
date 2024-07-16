<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="myutil.MySet"%>
<%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<%
	WebApplicationContext wac = 
		WebApplicationContextUtils.getWebApplicationContext(application);

	MySet mySet = wac.getBean("mySetBean",MySet.class);
	
	pageContext.setAttribute("mySet", mySet);
 %>  
 
 <hr>
 	과일목록
 <hr>
 <ul>
 	 <!-- for(String sido : sido_list)  -->
	 <c:forEach var="fruit" items="${ mySet.fruit_set }">
	  <li> ${ fruit } </li>
	 </c:forEach>
</ul>
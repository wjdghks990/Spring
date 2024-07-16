<%@page import="java.util.List"%>
<%@page import="myutil.MyList"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<%
	WebApplicationContext wac = 
		WebApplicationContextUtils.getWebApplicationContext(application);

	MyList myList = wac.getBean("myListBean",MyList.class);
	
	List sido_list = myList.getSido_list();
	
	pageContext.setAttribute("sido_list", sido_list);
 %>  
 
 <hr>
 	시도목록
 <hr>
 <ul>
 	 <!-- for(String sido : sido_list)  -->
	 <c:forEach var="sido" items="${ sido_list }">
	  <li> ${ sido } </li>
	 </c:forEach>
</ul>


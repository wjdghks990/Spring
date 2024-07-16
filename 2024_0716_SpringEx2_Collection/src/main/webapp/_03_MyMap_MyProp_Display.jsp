<%@page import="myutil.MyProp"%>
<%@page import="myutil.MyMap"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<%
	WebApplicationContext wac = 
		WebApplicationContextUtils.getWebApplicationContext(application);

 	MyMap myMap = wac.getBean("myMapBean",MyMap.class);
	
	pageContext.setAttribute("myMap", myMap); 
	
	MyProp myProp = wac.getBean("myPropBean",MyProp.class);
	
	pageContext.setAttribute("myProp", myProp);
	
 %>  
 
 <hr>
 	단어목록
 <hr>
 <ul>
 	 <!-- for(String sido : sido_list)  -->
	 <c:forEach var="word" items="${ myMap.wordMap }">
	  <li> ${ word } </li>
	 </c:forEach>
</ul> 
 <ul>
 	 <!-- for(String sido : sido_list)  -->
	 <c:forEach var="prop" items="${ myProp.dbProp }">
	  <li> ${ prop } </li>
	 </c:forEach>
</ul>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<hr>
객체(PersonVo)로 받기 결과
<hr>
이름: ${ requestScope.map.name }<br>
나이: ${ map.age }<br>
전화: ${ map.tel }<br>
전달: ${ ip }

<hr>
	Map forEach이용 출력
<hr>
<c:forEach var="entry" items="${ map }">
	parameter name : ${ entry.key }<br>
	parameter value : ${ entry.value }<br>
</c:forEach>

<a href="input.html">다시하기</a>
</body>
</html>
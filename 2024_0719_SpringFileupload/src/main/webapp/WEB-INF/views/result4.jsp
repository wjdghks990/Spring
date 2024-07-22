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
제목 : ${ requestScope.title }
<hr>
<c:forEach var="filename" items="${ filename_list }">
<img src="resources/images/${ filename }" width="100">
</c:forEach>

</body>
</html>
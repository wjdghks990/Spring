<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<img src="resources/images/${ filename1 }" width="200">
<img src="resources/images/${ filename2 }" width="200">

</body>
</html>
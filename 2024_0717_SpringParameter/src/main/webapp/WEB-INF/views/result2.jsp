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
객체(PersonVo)로 받기 결과
<hr>
이름: ${ requestScope.vo.name }<br>
나이: ${ vo.age }<br>
전화: ${ vo.tel }<br>

<a href="input.html">다시하기</a>
</body>
</html>
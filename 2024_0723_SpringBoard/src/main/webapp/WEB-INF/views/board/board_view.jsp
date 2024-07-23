<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script> 
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<style type="text/css">
	#box {
		width: 800px;
		margin: auto;
		margin-top: 1%;
	}
		@font-face {
	    font-family: 'ONE-Mobile-POP';
	    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2105_2@1.0/ONE-Mobile-POP.woff') format('woff');
	    font-weight: normal;
	    font-style: normal;
	}
	
	* {
		font-family: 'ONE-Mobile-POP';
		text-shadow: red;
		color: #F7230E;
	}
	
	textarea{
		resize: none;
	}
	
	h4{
		font-weight: bold;
	}
	
	.btn{
		color: white !important;
	}
	
	.common{
		border: 1px solid #eeeeee;
		padding: 5px;
		margin-bottom: 5px;
		box-shadow: 1px 1px 1px #f2f2f2;
	}
	
	.content{
		min-height: 120px;
	}
</style>
</head>
<body>
	<div id="box">
		<div class="panel panel-primary">
			<div class="panel-heading" style="background: #F7230E;"><b style="color: white;">${ vo.mem_name }</b>님의 글</div>
			<div class="panel-body">
				<div class="common subject">${ vo.b_subject }</div>
				<div class="common content">${ vo.b_content }</div>
				<div class="common regdate">${ vo.b_regdate }</div>
				
				<div>
					<input class="btn" style="background: brown;" type="button" value="목록보기"
						   onclick="location.href='list.do'">
						   
					<!-- 로그인된 상태에서만 등록가능 -->	
					<c:if test="${ not empty user }">  
						<input class="btn" style="background: #F7230E;" type="button" value="답글쓰기">
					</c:if> 
					
					<!-- 글의 주인만 수정/삭제 -->
					<c:if test="${ vo.mem_idx eq user.mem_idx }">
						<input class="btn" style="background: tomato;" type="button" value="수정하기">
						<input class="btn btn-danger" type="button" value="삭제하기">
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
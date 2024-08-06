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
	
	#cmt_content{
		width: 100%;
		height: 80px;
		resize: none;
	}
	
	#btn_cmt_register{
		width: 100%;
		height: 80px;
		background: pink;
	}
</style>

<script type="text/javascript">

	function del() {
		
		if(confirm("정말 삭제하시겠습니까?")==false)return;
		
		location.href = "delete.do?b_idx=${ vo.b_idx }";
		
	}
	
	/* 댓글쓰기 기능 */
	function comment_insert() {
		
		// 로그인이 안되었으면
		if("${ empty user }" == "true"){
			if(confirm("로그인 후 댓글쓰기가 가능합니다. \n 로그인하시겠습니까?")==false) return;
			
			//alert(location.href);
			
			// 로그인 폼으로 이동
			location.href="../member/login_form.do?url=" + encodeURIComponent(location.href);
			
			return;
		}
		
		// 댓글쓰기 작성 (ajax활용)
		let cmt_content = $("#cmt_content").val().trim();
		if(cmt_content=='') {
			alert("댓글을 입력하시오.");
			$("#cmt_content").val("");
			$("#cmt_content").focus();
			return;
		}
		
		// Ajax통해서 댓글 등록
		$.ajax({
			
			url		:	"../comment/insert.do",
			data	:	{
						 "cmt_content" : cmt_content,
						 "b_idx"	   : "${ vo.b_idx }",
						 "mem_idx"	   : "${ user.mem_idx }",
						 "mem_name"	   : "${ user.mem_name }",
						},
			dataType:	"json",
			
			success	:	function(res_data){
				// res_data = {"result" : true}
				
				// 입력창에서 작성했던 글 지우기
				$("#cmt_content").val("");
				
				if(res_data.result==false){
					alert("댓글등록 실패!!");
					return;
				}
				
				comment_list(1);
			},
			error	:	function(err){
				alert(err.responseText)
			}
		});
		
	} // end - comment_insert()
	
	var g_page = 1;
	// 댓글목록 요청
	function comment_list(page) {
		g_page = page;
		
		$.ajax({
			
			url		:	"../comment/list.do",
			data	:	{"b_idx":"${ vo.b_idx }",
						 "page" : page
						},
			success	:	function(res_data){
				
				$("#comment_display").html(res_data);
			},
			error	:	function(err){
				alert(err.responseText);
			}
			
		});
	} // end - comment_list()
		
	// 초기화 이벤트 : 시작시
	$(document).ready(function(){
		
		// 현재 게시물에 달린 댓글 목록 출력
		comment_list(1);
	});
		 

</script>

</head>
<body>
	<div id="box">
		<div class="panel panel-primary">
			<div class="panel-heading" style="background: #F7230E;"><b style="color: white;">${ vo.mem_name }</b>님의 글</div>
			<div class="panel-body">
				<div class="common subject">${ vo.b_subject }</div>
				<div class="common content">${ vo.b_content }</div>
				<div class="common regdate">${ vo.b_regdate }(${ vo.b_ip })</div>
				
				<div>
					<input class="btn" style="background: brown;" type="button" value="목록보기"
						   onclick="location.href='list.do'">
						   
					<!-- 로그인된 상태에서만 등록가능 -->	
					<c:if test="${ (not empty user) and (vo.b_depth le 1) }">  
						<input class="btn" style="background: #F7230E;" type="button" value="답글쓰기"
							   onclick="location.href='reply_form.do?b_idx=${ vo.b_idx }'">
					</c:if> 
					
					<!-- 글의 주인만 수정/삭제 -->
					<c:if test="${ vo.mem_idx eq user.mem_idx }">
						<input class="btn" style="background: tomato;" type="button" value="수정하기"
							   onclick="location.href='modify_form.do?b_idx=${ vo.b_idx }'">
						<input class="btn btn-danger" type="button" value="삭제하기"
							   onclick="del('${ vo.b_idx }');">
					</c:if>
				</div>
			</div>
		</div>
		
		<!-- 댓글 처리... -->
		<div class="row">
			<div class="col-sm-10" style="padding-right:0">
				<textarea rows="3" id="cmt_content" placeholder="로그인 후에 댓글쓰기가 가능합니다."></textarea>
			</div>
			<div class="col-sm-2">
				<input id="btn_cmt_register" type="button" value="댓글쓰기" 
					   onclick="comment_insert();">
			</div>
		</div>
		
		<hr>
		
		<div id="comment_display">
		<%-- <c:forEach var="vo" items="${ list }">
				<!-- 자신의 글만 삭제메뉴 활성화 -->
				<c:if test="${ user.mem_idx eq vo.mem_idx }">
					<div style="text-align: right;">
						<input type="button" value="x"
							style="background: #F7230E; color: white;"
							onclick="comment_delete('${ vo.cmt_idx}');">
					</div>
				</c:if>
				<div>${ vo.mem_name }</div>
				<div>${ vo.cmt_regdate }</div>
				<div>${ vo.cmt_content }</div>
				<hr>
			</c:forEach> --%>
		</div>
	</div>
</body>
</html>
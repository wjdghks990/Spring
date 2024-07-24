<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		width: 600px;
		margin: auto;
		margin-top: 10%;
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
</style>

<script type="text/javascript">

	function send(f) {
		
		let b_subject = f.b_subject.value.trim();
		let b_content = f.b_content.value.trim();
		
		if(b_subject==''){
			alert("제목을 입력하세요.")
			f.b_subject.value="";
			f.b_subject.focus();
			return;
		}
		if(b_content==''){
			alert("내용을 입력하세요.")
			f.b_content.value="";
			f.b_content.focus();
			return;
		}
		
		f.action = "reply.do";
		f.submit();
	}
	
</script>
</head>
<body>
<form>
	<input type="hidden" name="b_idx" value="${ param.b_idx }">
	<div id="box">
		<div class="panel panel-primary">
			<div class="panel-heading" style="background: #F7230E !important;">
				<h4>답글쓰기</h4>
			</div>
			<div class="panel-body">
				<div>
					<h4>제목 :</h4> <input class="form-control" name="b_subject">
				</div>
				<div>
					<h4>내용 :</h4> <textarea class="form-control" rows="10" name="b_content"></textarea>
				</div>
				
				<div style="margin-top: 10px;">
					<input class="btn" style="background: brown;" type="button" value="목록보기" onclick="location.href='list.do'">
					<input class="btn btn-danger" type="button" value="글올리기" onclick="send(this.form)">
				</div>
			</div>
		</div>
	</div>
</form>	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	
	function send1(f) {
		
		let title = f.title.value.trim();
		let photo = f.photo.value;
		
		if(title=='') {
			alert("제목을 입력하시오.");
			f.title.value="";
			f.title.focus();
			return;
		}
		
		if(photo==""){
			alert("사진을 선택하세요!");
			return;
		}
		
		f.action = "upload1.do";
		f.submit();
	}
	
	function send2(f) {
		
		let title = f.title.value.trim();
		let photo = f.photo.value;
		
		if(title=='') {
			alert("제목을 입력하시오.");
			f.title.value="";
			f.title.focus();
			return;
		}
		
		if(photo==""){
			alert("사진을 선택하세요!");
			return;
		}
		
		f.action = "upload2.do";
		f.submit();
	}

</script>

</head>
<body>

<hr>
	파일 1개 업로드
<hr>
<!-- 파일 업로드 인코딩 타입: multipart/form-data -->
<form method="POST" enctype="multipart/form-data">
	<div>
		제목 : <input name="title">
	</div>
	
	<div>
		사진 : <input type="file" name="photo">
	</div>
	
	<div>
		<input type="button" value="낱개로 받기" onclick="send1(this.form)">
		<input type="button" value="객체로 받기" onclick="send2(this.form)">
	</div>
	
</form>



</body>
</html>
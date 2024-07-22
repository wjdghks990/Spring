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
		let photo1 = f.photo[0].value;
		let photo2 = f.photo[1].value;
		
		if(title=='') {
			alert("제목을 입력하시오.");
			f.title.value="";
			f.title.focus();
			return;
		}
		
		if(photo1==""){
			alert("사진1을 선택하세요!");
			return;
		}
		
		if(photo2==""){
			alert("사진2를 선택하세요!");
			return;
		}
		
		f.action = "upload3.do";
		f.submit();
	}	

</script>

</head>
<body>

<hr>
	파일 2개 업로드<br>
	(Spring에서는 2개이상의 파일을 업로드시, parameter이름은 동일하게 부여해야 함)
<hr>
<!-- 파일 업로드 인코딩 타입: multipart/form-data -->
<form method="POST" enctype="multipart/form-data">
	<div>
		제목 : <input name="title">
	</div>
	
	<div>
		사진1 : <input type="file" name="photo">
	</div>
	
	<div>
		사진2 : <input type="file" name="photo">
	</div>
	
	<div>
		<input type="button" value="2개파일업로드" onclick="send1(this.form)">
	</div>
	
</form>
</body>
</html>
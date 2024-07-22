<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	
	function send(f) {
		
		let title = f.title.value.trim();
		
		if(title=='') {
			alert("제목을 입력하시오.");
			f.title.value="";
			f.title.focus();
			return;
		}
		
		if(f.photo.files.length==0 || f.photo.files.length>5){ 
			alert("업로드할 이미지를 1~5개 내에서 선택하시오.");
			return;
		}
		
		f.action = "upload4.do";
		f.submit();
	}	

</script>

</head>
<body>

<hr>
	파일 n개 업로드<br>
	(Spring에서는 n개이상의 파일을 업로드시, parameter이름은 동일하게 부여해야 함)
<hr>
<!-- 파일 업로드 인코딩 타입: multipart/form-data -->
<form method="POST" enctype="multipart/form-data">
	<div>
		제목 : <input name="title">
	</div>
	
	<div>
		사진1 : <input type="file" name="photo" multiple="multiple">
	</div>

	<div>
		<input type="button" value="n개파일업로드" onclick="send(this.form)">
	</div>
	
</form>
</body>
</html>
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
	#box{
		width: 600px;
		margin: auto;
		margin-top: 100px;
		
	}
	
	textarea {
		resize: none;
	}
	
	th {
		width: 15%;
		vertical-align: middle !important;
	}
</style>

<script type="text/javascript">
	function send(f) {
		
		//입력값 검증
		let name	= f.name.value.trim();
		let content = f.content.value.trim();
		let pwd		= f.pwd.value.trim();
		
		if(name=='') {
			alert('작성자명을 입력하시오.');
			f.name.value = "";
			f.name.focus();
			return;
		}
		if(content=='') {
			alert('내용을 입력하시오.');
			f.content.value = "";
			f.content.focus();
			return;
		}
		if(pwd=='') {
			alert('비밀번호를 입력하시오.');
			f.pwd.value = "";
			f.pwd.focus();
			return;
		}
		
		//f.method = "post";
		f.action = "insert.do"; // 전송대상(VisitInsertAction)
		f.submit();	// 전송
	}

</script>

</head>
<body>
	
	<form>
		<div id="box">
		<!-- 코드 정렬 : ctrl + shift + f -->
			<div class="panel panel-primary">
				<div class="panel-heading"><h4>글쓰기</h4></div>
				<div class="panel-body">
					
					<table class="table">
						<tr>
							<th>작성자</th>
							<td><input class="form-control" name="name" required="required"></td>
						</tr>
						
						<tr>
							<th>내용</th>
							<td>
								<textarea class="form-control" rows="6" name="content"></textarea>
							</td>
						</tr>
						
						<tr>
							<th>비밀번호</th>
							<td><input class="form-control" name="pwd" type="password"></td>
						</tr>
						
						<tr>
							<td colspan="2" align="center">
								<input class="btn btn-success" type="button" value="목록보기" 
									   onclick="location.href='list.do'">
								<input class="btn btn-primary" type="button" value="글올리기"
										onclick="send(this.form);">
							</td>
						</tr>
						
					</table>
				</div>
			</div>
		</div>
	</form>
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="//cdn.ckeditor.com/4.22.1/full/ckeditor.js"></script>

<style type="text/css">
	#box{ 
	     width: 1000px;
	     height:auto; 
     } 

</style>

<script>


function send(f)
{ 
     var content = CKEDITOR.instances.content.getData();

}
</script>

</head>
<body>

<form>
<div id="box">
	<textarea  id="content" rows="10" cols="" ></textarea>
	<script>
		// Replace the <textarea id="editor1"> with a CKEditor
		// instance, using default configuration.
		CKEDITOR.replace( 'content', {
			  	versionCheck: false,	
			  	width:'100%',
			  	height:'400px',
				filebrowserUploadUrl: '${pageContext.request.contextPath}/ckeditorImageUpload.do'	
		});
			
		CKEDITOR.on('dialogDefinition', function( ev ){
	            var dialogName = ev.data.name;
	            var dialogDefinition = ev.data.definition;
	          
	            switch (dialogName) {
	                case 'image': //Image Properties dialog
			              //dialogDefinition.removeContents('info');
			              dialogDefinition.removeContents('Link');
			              dialogDefinition.removeContents('advanced');
			              break;
		    }
		 });
	</script>
</div>
</form>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--
	application/x-www-form-urlencoded >> querystring 문자열 직렬형태로 전송규칙
	multipart/form-data
-->
<form method="post" enctype="multipart/form-data">
	<input type="file" multiple name="file">
	<input type="text" name="id">
	<button>전송</button>
</form>

</body>
</html>
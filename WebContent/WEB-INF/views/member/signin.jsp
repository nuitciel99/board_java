<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>회원제 게시판 - 로그인</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="../css/style.css">
    
</head>
<body>
	<%@ include file="../common/header.jsp" %>
    <main class="signin">
        <form method="post">
            <div class="form-control">
                <p><label for="id">id</label></p>
                <input type="text" id="id" name="id" placeholder="로그인할 id를 입력(4글자 이상)">
            </div>
            <div class="form-control">
                <p><label for="password">password</label></p>
                <input type="password" id="password" name="password" placeholder="비밀번호를 입력(4글자 이상)">
            </div>
            <br>
            
            <button class="btn btn-block btn-primary">로그인</button>
        </form>
    </main>
    <%@ include file="../common/footer.jsp" %>
</body>
</html>
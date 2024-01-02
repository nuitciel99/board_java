<%@page import="domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>회원제 게시판 - 비밀번호 변경</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="${cp}/css/style.css">
    
</head>
<body>
 	<%@ include file="../common/header.jsp" %>
    <main class="signin">
    	<h2>회원 정보 수정</h2>
        <form method="post">
            <div class="form-control">
                <p><label for="oldpassword">기존 password</label></p>
                <input type="password" id="oldpassword" name="oldpassword" placeholder="비밀번호를 입력(4글자 이상)">
            </div>
            <div class="form-control">
                <p><label for="password">신규 password</label></p>
                <input type="password" id="password" name="password" placeholder="신규 비밀번호를 입력(4글자 이상)">
            </div>
            <div class="form-control">
                <p><label for="passwordchk">신규 password</label></p>
                <input type="password" id="passwordchk" name="passwordchk" placeholder="신규 비밀번호를 입력(4글자 이상)">
            </div>
            <br>
            <!-- 부모 너비 값을 그대로 쓰기 위해 btn-block -->
            <button class="btn btn-block btn-primary">비밀번호 변경</button>
        </form>
    </main>
    <%@ include file="../common/footer.jsp" %>
</body>
</html>
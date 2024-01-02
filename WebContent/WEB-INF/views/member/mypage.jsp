<%@page import="domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>회원제 게시판 - 마이페이지</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="../css/style.css">
    
</head>
<body>
 	<%@ include file="../common/header.jsp" %>
 	<% 
 	Member member = (Member)request.getAttribute("myMember");
 	%>
    <main class="signin">
    <h2>회원 정보 수정</h2>
        <form method="post">
            <div class="form-control">
                <p><label for="id">id</label></p>
                <input type="text" id="id" name="id" readonly value="<%=member.getId()%>">
            </div>
            <div class="form-control">
                <p><label for="name">name</label></p>
                <input type="text" id="name" name="name" value="<%=member.getName()%>">
            </div>
            <div class="form-control">
                <p><label for="email">email</label></p>
                <input type="text" id="email" name="email" value="<%=member.getEmail()%>">
            </div>
            <br>
            <!-- 부모 너비 값을 그대로 쓰기 위해 btn-block -->
            <button class="btn btn-block btn-primary">회원정보수정</button>
            <a href="/member/changePwd">비밀번호 변경</a>
            <a href="">회원 탈퇴</a>
        </form>
    </main>
    <%@ include file="../common/footer.jsp" %>
</body>
</html>
<%@page import="domain.Member"%>
<%@page import="domain.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>회원제 게시판 - 게시글 수정</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="../css/style.css">
    <style>
		/* .board h2{margin: 30px; text-align: center;}
		.board-list{width: 100%; border-collapse: collapse; margin: 10px auto 50px;}
		.board-list th, .board-list td {padding: 10px; border-top: 1px solid black; border-bottom: 1px solid black;}
		.board-list td:not(:nth-child(2)) {text-align: center;}
		.board-list td:nth-child(2) {width: 60%;} */
    </style>
</head>
<body>
	<c:import url="../common/header.jsp" />
	<%-- <%
		String id = ((Member)session.getAttribute("member")).getId();
	%> --%>
    <main class="width-wrap board">
    <form method="post" action="modify">
        <%-- ${list} --%>
        <h2>게시글 수정</h2>
        <table class="board-view">
        	<%-- <c:forEach items="${view}" var="w"/> --%>
        	<tr>
        		<th>카테고리</th>
        		<td>
        			<select name="category">
        				<c:forEach items="${categories}" var="c">
        				<option value="${c.cno}" ${c.cno == cri.category ? 'selected' : ''}>${c.cdesc}</option>
        				</c:forEach>
        			</select>
        		</td>
        		<th>작성자</th>
        		<td>${board.id}</td>
        	</tr>
        	<tr>
        		<th>제목</th>
        		<td colspan="3"><input type="text" name="title" value="${board.title}"></td>
        	</tr>
        	<tr>
        		<td colspan="4"><textarea name="content">${board.content}</textarea></td>
        	</tr>
        </table>
        <input type="hidden" name="id" value="${member.id}">
        <input type="hidden" name="bno" value="${board.bno}">
        <input type="hidden" name="pageNum" value="${cri.pageNum}">
        <input type="hidden" name="amount" value="${cri.amount}">
        <input type="hidden" name="type" value="${cri.type}">
        <input type="hidden" name="keyword" value="${cri.keyword}">
        
        <div class="btn-area"><button class="btn btn-primary">수정</button></div>
    </form>
    </main>
    <jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
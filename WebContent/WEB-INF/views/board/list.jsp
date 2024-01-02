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
    <title>회원제 게시판 - 게시글 목록</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-1ycn6IcaQQ40/MKBW2W4Rhis/DbILU74C1vSrLJxCq57o941Ym01SwNsOMqvEBFlcgUa6xLiPY/NS5R+E6ztJQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
		.search-area form{width: 50%}
    </style>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
	<c:import url="../common/header.jsp" />
	<%-- <%@ include file="../common/header.jsp" %> --%>
	
    <main class="width-wrap board">
        <%-- ${list} --%>
        <%-- ${categories.stream().filter(s->s.getCname().equals(param.category)).findFirst().get().getCdesc()} --%>
        
        <h2>
		<c:forEach items="${categories}" var="c">
        	<c:if test="${c.cno == pageDto.cri.category}">${c.cdesc}</c:if>
        </c:forEach>
		- 게시글 목록
		</h2>
		<div class="search-area">
			<form>
				<input type="hidden" name="category" value="${pageDto.cri.category}">
				<input type="hidden" name="amount" value="${pageDto.cri.amount}">
				<input type="hidden" name="pageNum" value="${pageDto.cri.pageNum}">
				<select name="type">
					<option value="">검색 조건을 선택하세요</option>
					<c:set value="제목,내용,작성자,제목+내용,제목+작성자,내용+작성자,제목+내용+작성자" var="val" />
					<c:forTokens items="T,C,I,TC,TI,CI,TCI" delims="," var="i" varStatus="stat">
						<option value="${i}" ${pageDto.cri.type == i ? 'selected' : '' }>${val.split(",")[stat.index]}</option>
					</c:forTokens>
				</select>
				<input name="keyword" value="${pageDto.cri.keyword}">
				<button class="btn btn-primary">검색</button>
			</form>
			<select id="selectAmount">
				<c:forTokens items="5,10,25,50" delims="," var="i">
					<option ${i == pageDto.cri.amount ? 'selected' : ''} value="${i}">${i}개씩보기</option>
				</c:forTokens>
			</select>
			<script>
				$("#selectAmount").change(function(){
					const amount = this.value;
					console.log(amount);
					
					const val = $(".search-area form")
						.find("[name='amount']").val(amount)
					.end().serialize();
					
					console.log(val);
					location.href = '?' + val;
				});
					
				$(".search-area form").submit(function(){
					if(!this.type.value.trim()){
						alert("검색 조건을 입력하세요");
						return false;
					}
					if(!this.keyword.value.trim()){
						alert("검색어를 입력하세요");
						return false;
					}
				});
				
				
			</script>
		</div>
		<style>
			.board-list .board-list-reply-cnt {font-size: 13px; color: #bbb;}
			.board-list .board-list-attached {font-size: 13px; color: #444;} 
		</style>
        <table class="board-list">
        	<tr>
        		<th>글번호</th>
        		<th>제목</th>
        		<th>작성자</th>
        		<th>작성일</th>
        	</tr>
    		<c:forEach items="${list}" var="b">
    		
        	<tr>
        		<td>${b.bno}</td>
        		<td>
        			<a href="view?bno=${b.bno}&${pageDto.cri.qs}"><c:out value="${b.title}" /></a> 
        			<span class="board-list-reply-cnt">[${b.replyCnt}]</span> 
        			<span class="board-list-attached">${b.attached ? '<i class="fas fa-paperclip"></i>' : ''}</span>
        		</td>
        		<td>${b.id}</td>
        		<td>${b.regDate}</td>
        	</tr>
     		</c:forEach>
        </table>
        <div class="pagination">
        	<c:if test="${pageDto.prevs}">
        		<a class="direct" href="?pageNum=${pageDto.startPage - 1}&${pageDto.cri.qs2}" title="이전 페이지 구역으로"><i class="fas fa-angle-double-left"></i></a>
        	</c:if>
        	<c:if test="${pageDto.prev}">
        		<a class="direct" href="?pageNum=${pageDto.cri.pageNum - 1}&${pageDto.cri.qs2}" title="이전 페이지로"><i class="fas fa-angle-left"></i></a>
        	</c:if>
        	
        	<c:forEach begin="${pageDto.startPage}" end="${pageDto.endPage}" var="i">
        		<a class="${i == pageDto.cri.pageNum ? 'active' : ''}" href="?pageNum=${i}&${pageDto.cri.qs2}" title="${i}페이지로">${i}</a>
        	</c:forEach>
        	
        	<c:if test="${pageDto.next}">
        		<a class="direct" href="?pageNum=${pageDto.cri.pageNum + 1}&${pageDto.cri.qs2}" title="다음 페이지"><i class="fas fa-angle-right"></i></a>
        	</c:if>
        	<c:if test="${pageDto.nexts}">
        		<a class="direct" href="?pageNum=${pageDto.endPage + 1}&${pageDto.cri.qs2}" title="다음 페이지 구역으로"><i class="fas fa-angle-double-right"></i></a>
        	</c:if>
        </div>
        <div class="btn-area"><a href="write?${pageDto.cri.qs}">글쓰기</a></div>
    </main>
    <jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
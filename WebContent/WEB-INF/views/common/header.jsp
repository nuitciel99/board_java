<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>

</style>

<header>
	<div class="logo-area">
		<a href="${cp}/">
	    <div class="width-wrap">
	        <h1>회원제 게시판</h1>
	    </div>
	    </a>
	</div>
	
	<nav>
	    <ul class="width-wrap gnb clearfix">
	        <li><a href="${cp}/main">홈</a></li>
	        <li><a href="${cp}/board/list">게시판</a>
	        	<ul>
	        		<c:forEach items="${categories}" var="c">
	        		<li><a href="${cp}/board/list?category=${c.cno}">${c.cdesc}</a></li>
	        		</c:forEach>
	        	</ul>
	        </li>
	    </ul>
	    
	</nav>
</header>

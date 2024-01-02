<%@page import="domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<meta http-equiv='X-UA-Compatible' content='IE=edge'>
<title>회원제 게시판</title>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<link rel="stylesheet" href="css/style.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js" integrity="sha512-3j3VU6WC5rPQB4Ld1jnLV7Kd5xr+cq9avvhwqzbH/taCRNURoeEpoPBK9pDyeukwSxwRPJ8fDgvYXd6SkaZ2TA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    
    
</head>
<body>
    <%@ include file="common/header.jsp" %>
    <main class="index width-wrap clearfix">
        <style>
        	.main-board {width: 300px; float: left; margin-right: 15px; margin-bottom: 30px;}
        	.main-board > h3 {margin-bottom: 16px;}
        	.main-board ul {font-size: 14px;}
        	.main-board ul li a{overflow-x: hidden;}
        </style>
        <article class="clearfix">
        	<c:forEach items="${categories}" var="c">
	        	<div class="main-board">
	        		<h3 class="text-center">${c.cdesc}</h3>
	        		<ul>
	        			<c:forEach items="${list}" var="b">
	        				<c:if test="${c.cno == b.category}">
		        				<li><a href="/board/view?bno=${b.bno}&category=${c.cno}">${b.title}</a></li>
		        			</c:if>
	        			</c:forEach>
	        		</ul>
	        	</div>
        	</c:forEach>
        </article>
        <aside>
        <!-- 
        	cookie - 클라이언트와의 연결정보를 클라이언트측에 저장한 뒤에 웹 서버가 클라이언트의 웹브라우저에서 쿠키를 읽어서 사용
        	>>> 연결이 끊겼을 때 정보들을 지속적으로 유지하기 위한 수단. 쿠키는 서버에서 생성하고 클라이언트측에 저장된다. 
        		서버에 요청할 때마다 쿠키의 속성값이 변경, 참조될 수 있다. 쿠키는 로컬에 txt파일 형태로 저장되어 
        		보안에 취약하다는 단점이 있다. 따라서 최근 웹브라우저들은 쿠키생성을 차단하기도 한다.
        	
        	session - 클라이언트와의 연결정보를 서버에서 관리
        	>>> 쿠키와 마찬가지로 서버와의 연결이 끊겼을 때 데이터를 유지하는 수단이다. 단, 쿠키와는 달리 서버상에 객체로 저장하게 된다.
				클라이언트의 요청이 발생하면 자동으로 생성되며 세션 내부객체의 메소드를 이용하여 속성을 설정한다.
				세션은 서버에서만 접근이 가능하여 보안이 쿠키보다 강하고, 데이터의 용량에 제한이 없다.
         -->
        <c:if test="${empty member}">
        	<a href="member/signin">로그인</a>
            <a href="member/signup">회원가입</a>
            <!-- <a href="finduser.html">id/pw찾기</a> -->
        </c:if>
        <c:if test="${not empty member}">
        	<h4>${member.id}(${member.name})님 환영합니다</h4>
        	<a href="member/mypage">마이페이지</a>
        	<a href="member/logout">로그아웃</a>
        </c:if>
        
        	
        </aside>
    </main>
    
    <%@ include file="common/footer.jsp" %>
    
    <div class="layer">
        <img src="https://image.yes24.com/momo/scmfiles/AdvertFiles/202309/2575884_230905060221.jpg" alt="팝업">
        <p><label>오늘은 그만 보기 <input type="checkbox"></label><a href="#">닫기</a></p>
    </div>
    
    <script>
	    $(function(){
	        $(".layer a").click(function(){
	            $(".layer").hide();
	        });
	
	        $(".layer input:checkbox").change(function(){
	            // 24시간 동안 안 보이기 
	            // 쿠키 생성 
	            $.cookie("popup", "off", {expires:1, path:'/'})
	            $(".layer").hide();
	        });
	        // cookie 없으면 보이기 
	        $.cookie("popup") !== "off" && $(".layer").show();
	
	    });
	</script>
</body>
</html>
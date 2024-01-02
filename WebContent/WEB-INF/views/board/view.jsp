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
    <title>회원제 게시판 - 게시글 상세 조회</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-1ycn6IcaQQ40/MKBW2W4Rhis/DbILU74C1vSrLJxCq57o941Ym01SwNsOMqvEBFlcgUa6xLiPY/NS5R+E6ztJQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment-with-locales.min.js" integrity="sha512-42PE0rd+wZ2hNXftlM78BSehIGzezNeQuzihiBCvUEB3CVxHvsShF86wBWwQORNxNINlBPuq7rG4WWhNiTVHFg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    
    <style>
		
    </style>
</head>
<body>
	<c:import url="../common/header.jsp" />
    <main class="width-wrap board">
        <h2>게시글 상세 조회</h2>
        <table class="board-view">
        	
        	<tr>
        		<th>글번호</th>
        		<td>${board.bno}</td>
        		<th>작성자</th>
        		<td>${board.id}</td>
        	</tr>
        	<tr>
        		<th>작성일</th>
        		<td>${board.regDate}</td>
        		<th>최종수정일</th>
        		<td>${board.updateDate}</td>
        	</tr>
        	<tr>
        		<th>제목</th>
        		<td colspan="3"><c:out value="${board.title}" /></td>
        	</tr>
        	<tr>
        		<th>첨부 파일</th>
        		<td colspan="3">
        			<c:forEach items="${board.attachs}" var="attach" >
					<p><a href="${cp}/file?${attach.qs}">${attach.origin}</a></p>
					</c:forEach>	
        		</td>
        	</tr>
        	<tr>
        		<td colspan="4" class="td-content"><c:out value="${board.content}"/></td>
        	</tr>
        </table>
        <div class="btn-area">
        	<c:if test="${member.id == board.id || member.admin}">
	        	<a href="modify?bno=${board.bno}&${cri.qs}">수정</a>
	        	<a href="remove?bno=${board.bno}&${cri.qs}">삭제</a>
        	</c:if>
        	<a href="list?${cri.qs}">목록</a>
        </div>
        
        <hr>
        <style>

        </style>
        <div class="reply-area">
	        <h3><i class="far fa-comment-dots"></i>Reply</h3>
	        <div class="reply-write" id="replyWrite">
	        	<h4>댓글작성 <span class="reply-cnt"></span></h4>
	        	<c:if test="${empty member}">
	        		<div class="reply-not-login">댓글을 작성하려면 로그인해야 합니다.</div>
	        	</c:if>
	        	<c:if test="${not empty member}">
	        	<h4>${member.id}</h4>
	        	<textarea placeholder="댓글을 작성하세요."></textarea>
	        	<div class="text-right">
		        	<p><span class="reply-write-cnt">0</span>/500</p>
		        	<button class="btn btn-primary btn-reply-register">등록</button>
	        	</div>
	        	</c:if>
	        </div>
	        <style>
	        
	        </style>
	        <ul class="replies">
	        	
	        </ul>
	        <style>
	        .reply-showmore-area {padding: 10px 50px;}
	        .btn-primary:disabled {background-color: #887454; border-color: #887454;}
	        </style>
	        <div class="reply-showmore-area">
	        	<button class="btn btn-primary btn-block">더보기</button>
	        </div>
        </div>
    </main>
    <script>
    const bno = '${board.bno}';
    const cp = '${cp}';
	const id = '${member.id}';
    
    moment.locale('ko');
    
    /*
    	input text into reply-cnt
    */
    function applyReplyCount(){
    	$.ajax(cp+"/reply/cnt/"+bno).done((data) => {
    		console.log(data);
    		$(".reply-cnt").text(data);
    	})
    }
    applyReplyCount();
    
    /* 
    	get reply for 목록, 댓글 작성, 댓글 수정  
    */
    function getReplyLi(reply){
    	return  `
		<li data-rno='\${reply.rno}'>
			<div class="clearfix">
				<p class="reply-writer">\${reply.id}</p><a href="" class="reply-menu-link">\${id === reply.id ? '<i class="fas fa-ellipsis-v"></i>' : ''}</a>
			</div>
			<p class="reply-content">\${reply.content}</p>
	    	<p class="reply-regdate">\${moment(reply.regDate).fromNow()}</p>
	    	<div class="reply-tooltip">
	    		<a href="">수정</a>
	    		<a href="">삭제</a>
			</div>
		</li>
		`;
    }
    
	// 댓글 더보기 버튼
	$(".reply-showmore-area button").click(()=>{
		console.log("더보기 클릭");
		const lastRno = $(".replies li").last().data("rno");
		getList(lastRno);
		
	})
    
    // 비로그인 시 로그인 전환 이벤트
    $("#replyWrite .reply-not-login").click(function(){
    	console.log("click");
    	location.href = cp + "/member/signin?href=" + encodeURIComponent(location.href + "#replyWrite");
    });
    
    
    // 작성된 댓글의 글자수 계산/ 제한
    $(".reply-area").on("keyup", "textarea", function(){
    	const length = this.value.length;
    	$(this).next().find(".reply-write-cnt").html(length);
    	
    	if(length > 500){
    		alert("댓글 입력 글자 수는 500 글자입니다.");
    		this.value = this.value.slice(0, 500);
    		$(this).next().find(".reply-write-cnt").html(500);
    	}
    });
    
    
    // 댓글 작성
    $(".btn-reply-register").click(function(){
    	if(!confirm("댓글을 등록하시겠습니까??")){
    		return;
    	}
    	
    	const content = $("#replyWrite textarea").val().trim();
    	if(!content){
    		alert("댓글을 입력하세요");
    		$("#replyWrite textarea").focus();
    		return;
    	}
    	
    	const reply = {bno, id, content};
    	console.log(reply);
    	console.log(JSON.stringify(reply));
    	
    	$.ajax(cp+"/reply", {
    		method : "post",
    		data : JSON.stringify(reply),
    		success : function(data){
    			// data type : String
    			console.log(data);
    			alert("댓글이 등록되었습니다.");
    			// location.reload();
    			// applyReplyCount();
    			$(".reply-cnt").text((i, val) => +val + 1);
    			
    			$.ajax(cp+"/reply/"+data).done(function(data){
    				// data type : reply, getReplyLi(data) : reply 하나 
    				$(".replies").prepend(getReplyLi(data));
    				$("#replyWrite textarea").val("");
    			});
    		}
    	})
    });
    
    
    
    // 목록
    // \ 안 쓰면 EL 
    function getList(lastRno){
    	const url = cp+"/reply/list/"+bno + (lastRno ? "/" + lastRno : "")
    	
    	$.ajax(url).done((data)=>{
			let str = '';
			for(let i of data){
				str += getReplyLi(i);
			}
			$(".replies").append(str);
			
			// list : $(".replies li")
			if($(".replies li").length >= $(".reply-cnt").html()){
				$(".reply-showmore-area button").prop("disabled", true).text("마지막 댓글입니다");
			}
			// reply
			// $(".reply-cnt").text();
		})
    }
    getList();
    
    
    // tooltip 이벤트 : 위임 개념 적용
    $(".replies").on("click", ".reply-menu-link", function(){
    	event.preventDefault();	
		// $(".reply-tooltip").removeClass("active");
		
    	if($(".replies textarea").length && !confirm("현재 수정 중인 댓글 내용이 반영되지 않았습니다. 계속 하시겠습니까??")){
			return;
    	}
		
		const $target = $(this).closest("li").find(".reply-tooltip");
		const flag = $target.hasClass("active");
		$(".reply-tooltip").removeClass("active");
		flag || $target.addClass("active");
		
		// console.log("활성화된 수정폼 갯수", $(".replies textarea").length);
		
		$(".replies textarea").each(function(){
			const $li = $(this).closest("li");
			const rno = $li.data("rno");
			$.ajax(cp+"/reply/"+rno).done(function(data){
				$li.find(".reply-content").html(data.content);
			});
		});
		
	});
	
	// tooltip 삭제 click event
	$(".replies").on("click", ".reply-tooltip a:last-child", function(){
		event.preventDefault();	
		if(!confirm("댓글을 삭제하시겠습니까??")){
    		return;
    	}
		
		console.log("click");
		const $li = $(this).closest("li");
		const rno = $li.data("rno");
		console.log(rno);

		$.ajax(cp+"/reply/"+rno, {
			method : "delete",
			success : function(data){
				console.log(data);
				// location.reload();
				$li.remove();
				//applyReplyCount();
				$(".reply-cnt").text((i, val) => +val - 1);
			}
		});
	})
	
	// tooltip 수정 click event
	$(".replies").on("click", ".reply-tooltip a:first-child", function(){
		event.preventDefault();	
		
		const $li = $(this).closest("li");
		const rno = $li.data("rno");

		$.ajax(cp+"/reply/"+rno).done(function(data){
				$li.find(".reply-content").html(`<textarea placeholder="댓글을 작성하세요.">\${data.content}</textarea>
	        	<div class="text-right">
		        	<p><span class="reply-write-cnt">\${data.content.length}</span>/500</p>
		        	<button class="btn btn-primary btn-reply-modify">수정</button>
	        	</div>`).end()
	        	.find(".reply-tooltip").removeClass("active");
		});
	})
	
	// 댓글 수정 
	$(".replies").on("click", ".btn-reply-modify", function() {
		if(!confirm("댓글을 수정하시겠습니까?")) {
			return;
		}
	
		const content = $(this).parent().prev().val().trim();
		if(!content){
			alert("댓글 내용을 입력하세요!!");
			$(this).parent().prev().focus();
			return;
		}
		const $li = $(this).closest("li");
		const rno = $li.data("rno");
		const reply ={rno, content};
		console.log(reply);
		console.log(JSON.stringify(reply));
		
		$.ajax(cp+"/reply",{
			method : "put",
			data : JSON.stringify(reply),
			success : function(data){
				console.log(data);
				alert("댓글이 수정되었습니다.");
				// location.reload();
				
				$.ajax(cp+"/reply/"+rno).done(function(data){
    				// data type : reply, getReplyLi(data) : reply 하나 
    				$li.replaceWith(getReplyLi(data));
    				$(".replies textarea").val("");
    			});
			}
		})
	});   
    
    
    </script>
    <%-- <%@ include file="../common/footer.jsp" %> --%>
    <jsp:include page="../common/footer.jsp" />
</body>
</html>
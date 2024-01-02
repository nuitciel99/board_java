package controller.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Member;
import service.MemberService;
import util.WebUtils;

@WebServlet("/member/signin")
public class Signin extends HttpServlet{
	// HttpServlet 상속 받았을때 구현 가능한 메서드
	// http method : get, post, put, delete

	private MemberService memberService = new MemberService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/member/signin.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("id");
		String pwd = req.getParameter("password");
		String href = req.getParameter("href");
		
//		Member member = Member.builder().id(id).pwd(pwd).build();
//		System.out.println(member);
		
		/*
		 * 쿠키
		 * 연결이 끊겼을 때 정보들을 지속적으로 유지하기 위한 수단. 쿠키는 서버에서 생성하고 클라이언트측에 저장된다.
		 * 서버에 요청할 때마다 쿠키의 속성값이 변경, 참조될 수 있다.
		 * 쿠키는 로컬에 txt파일 형태로 저장되기 때문에 보안에 취약하다는 단점이 있다. 
		 * 따라서 웹브라우저들은 쿠키생성을 차단하기도 한다.
		 * 
		 * 세션
		 * 쿠키와 마찬가지로 서버와의 연결이 끊겼을 때 데이터를 유지하는 수단이다. 단, 쿠키와는 달리 서버상에 객체로 저장하게 된다.
		 * 클라이언트의 요청이 발생하면 자동으로 생성되며 세션 내부객체의 메소드를 이용하여 속성을 설정한다.
		 * 세션은 서버에서만 접근이 가능하여 보안이 쿠키보다 강하고, 데이터의 용량에 제한이 없다.
		 */
		
		// 20230914 login 
		Member mem = memberService.findBy(id);
		if(mem != null && mem.getPwd().equals(pwd)) { // 로그인 성공
			req.getSession().setAttribute("member", mem);
			
			if(href != null) {
				href = URLDecoder.decode(href, "utf-8");
			}
			else {
				href = "/";
			}
			resp.sendRedirect(href == null ? "/" : href);
			
		}
		else { // 실패
			WebUtils.alert(resp, "로그인 실패", true);
			
//			resp.setContentType("text/html; charset=utf-8");  
//			PrintWriter pw = resp.getWriter();
//			pw.write("<script>\r\n");
//			pw.write("alert('로그인을 실패했습니다.');\r\n");
//			pw.write("history.back();\r\n");
//			pw.write("</script>\r\n");
			
		}
		
		
//		if(memberService.signup(member)) {
////			resp.sendRedirect("../main");
//			resp.setContentType("text/html; charset=utf-8"); // resp를 html text로 사용
//
//			PrintWriter pw = resp.getWriter();
//			pw.write("<script>\r\n");
//			pw.write("alert('회원 가입을 축하합니다.')\r\n");
//			pw.write("location.href='../main'\r\n");
//			pw.write("</script>\r\n");
//		}
//		else {
//			resp.setContentType("text/html; charset=utf-8");  
//			PrintWriter pw = resp.getWriter();
//			pw.write("<script>\r\n");
//			pw.write("alert('이미 존재하는 회원입니다.');\r\n");
//			pw.write("history.back();\r\n");
//			pw.write("</script>\r\n");
//		}
		
		// 회원가입
		// 1. id, pwd, name 파라미터 수집
		// 2. model 처리 >> 데이터베이스 저장
		// 3. main 이동 
	}
	
	
}

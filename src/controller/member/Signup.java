package controller.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Member;
import service.MemberService;
import util.WebUtils;

@WebServlet("/member/signup")
public class Signup extends HttpServlet{
	// HttpServlet 상속 받았을때 구현 가능한 메서드
	// http method : get, post, put, delete
	
//	@Override
//	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////		req.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(req, resp);
//	}
	private MemberService memberService = new MemberService();
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("id");
		String pwd = req.getParameter("password");
		String name = req.getParameter("name");
		String email= req.getParameter("email");
		
		Member member = Member.builder().id(id).pwd(pwd).name(name).email(email).build();
		System.out.println(member);
		
		if(memberService.signup(member)) {
			WebUtils.alert(resp, "회원 가입을 축하합니다", "/main");
////			resp.sendRedirect("../main");
//			resp.setContentType("text/html; charset=utf-8"); // resp를 html text로 사용
//
//			PrintWriter pw = resp.getWriter();
//			pw.write("<script>\r\n");
//			pw.write("alert('회원 가입을 축하합니다.')\r\n");
//			pw.write("location.href='../main'\r\n");
//			pw.write("</script>\r\n");
		}
		else {
			WebUtils.alert(resp, "이미 가입된 회원입니다.", true);
			
//			resp.setContentType("text/html; charset=utf-8");  
//			PrintWriter pw = resp.getWriter();
//			pw.write("<script>\r\n");
//			pw.write("alert('이미 존재하는 회원입니다.');\r\n");
//			pw.write("history.back();\r\n");
//			pw.write("</script>\r\n");
		}
		
		// 회원가입
		// 1. id, pwd, name 파라미터 수집
		// 2. model 처리 >> 데이터베이스 저장
		// 3. main 이동 
	}
	
	
}

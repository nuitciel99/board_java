package controller.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import domain.Member;
import service.MemberService;
@WebServlet("/member/changePwd")
public class ChangePwd extends HttpServlet{
	private MemberService memberService = new MemberService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/member/changePwd.jsp").forward(req, resp); 
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");  
		
		String oldpassword = req.getParameter("oldpassword");
		String password = req.getParameter("password");
		String passwordchk = req.getParameter("passwordchk");
		
		Object member = req.getSession().getAttribute("member");
		
		if(member == null) {
			PrintWriter pww = resp.getWriter();
			pww.write("<script>alert('로그인이 안되어있어요'); history.back();</script>");
//			resp.sendRedirect("/");
			return;
		}
		
		Member resultMember = memberService.findBy(((Member) member).getId());
		if(!resultMember.getPwd().equals(oldpassword) || !password.equals(passwordchk)) {
			PrintWriter pww = resp.getWriter();
			pww.write("<script>alert('이전 비밀번호와 같지 않거나 새 비밀번호와 새 비밀번호 확인이 같지 않습니다.'); history.back();</script>");
//			resp.sendRedirect("/");
			return;
		}
		
		// 
		memberService.modifyPwd((Member.builder().id(resultMember.getId())).pwd(password).build());
//		req.getSession().setAttribute("member", member);
		resp.sendRedirect("/member/mypage");
	}
	
}

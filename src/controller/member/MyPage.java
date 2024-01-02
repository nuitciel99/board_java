package controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Member;
import service.MemberService;
import util.WebUtils;
@WebServlet("/member/mypage")
public class MyPage extends HttpServlet{
	private MemberService memberService = new MemberService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Member member = WebUtils.getMember(req);
		if(member == null) {
			resp.sendRedirect("/");
			return;
		}
		req.setAttribute("myMember", memberService.findBy(member.getId()));
		req.getRequestDispatcher("/WEB-INF/views/member/mypage.jsp").forward(req, resp); 
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("id");
		String pwd = req.getParameter("password");
		String name = req.getParameter("name");
		String email= req.getParameter("email");
		
		Member member = Member.builder().id(id).name(name).email(email).build();
		System.out.println(member);
		
		memberService.modify(member);
		req.getSession().setAttribute("member", member);
		resp.sendRedirect("/member/mypage");
	}
	
}

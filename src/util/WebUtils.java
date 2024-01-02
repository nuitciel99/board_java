package util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Member;

public class WebUtils {
	public static Member getMember(HttpServletRequest req) {
		Member member = null;
		if(req.getSession().getAttribute("member")  != null) {
			member = (Member)req.getSession().getAttribute("member");
		}
		return member;
	}
	
	
	public static void alert(HttpServletResponse resp, String msg, String url, boolean back) throws IOException {
		
		resp.setContentType("text/html; charset=utf-8");  
		PrintWriter pw = resp.getWriter();
		pw.println("<script>");
		pw.println("alert('"+msg+"')");
		if(url != null) {
			pw.println("location.href = '"+ url + "'");
		}
		if(back) {
			pw.println("history.back();");
		}
		pw.println("</script>");
	}
	
	public static void alert(HttpServletResponse resp, String msg) throws IOException {
		alert(resp, msg, null);
		
	}
	
	public static void alert(HttpServletResponse resp, String msg, String url) throws IOException {
		
		alert(resp, msg, url, false);
	}
	
	
	public static void alert(HttpServletResponse resp, String msg, boolean back) throws IOException {
			
		alert(resp, msg, null, true);
	}
	
}

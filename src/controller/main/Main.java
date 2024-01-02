package controller.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardService;

@WebServlet("/main")
public class Main extends HttpServlet{
	private BoardService service = new BoardService();
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("list", service.getTop(5));
		req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
	}
	
}

package controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Criteria;
import domain.PageDto;
import service.BoardService;
@WebServlet("/board/list")
public class BoardList extends HttpServlet{
	private BoardService boardService = new BoardService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String cate = req.getParameter("category");
		String pageNum = req.getParameter("pageNum");
		String amount = req.getParameter("amount");
		String type = req.getParameter("type");
		String keyword = req.getParameter("keyword");

		Criteria.CriteriaBuilder cb = Criteria.builder();
		if(cate != null) {
			cb.category(Integer.parseInt(cate));
		}
		if(pageNum != null) {
			cb.pageNum(Integer.parseInt(pageNum));
		}
		if(amount != null) {
			cb.amount(Integer.parseInt(amount));
		}
		if(type != null) {
			cb.type(type);
		}
		if(keyword != null) {
			cb.keyword(keyword);
		}
		
		Criteria criteria = cb.build();
		
		/*
		 * getCount : 해당 카테고리의 글 수를 set
		 * boardService.list : criteria를 리턴 받아 해당 카테고리의 게시글 set
		 */
		req.setAttribute("pageDto", new PageDto(criteria, boardService.getCount(criteria)));
		req.setAttribute("list", boardService.list(criteria));
		req.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(req, resp);
		
	}
	
	
}

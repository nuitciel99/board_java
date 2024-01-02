package controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Board;
import domain.Criteria;
import service.BoardService;
import util.WebUtils;
@WebServlet("/board/view")
public class BoardView extends HttpServlet{
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
		
		if(req.getParameter("bno") == null) {
			WebUtils.alert(resp, "없는 게시글 번호 입니다", "list?" + criteria.getQs());
			return;
		}
		
		Long bno = Long.valueOf(req.getParameter("bno"));
		Board board = boardService.findBy(bno);
		if(board == null) {
			// 게시글 없음
			// 목록으로 보내기
			WebUtils.alert(resp, "없는 게시글 입니다", "list?" + criteria.getQs());
			return;
		}
		
		req.setAttribute("cri", criteria);
		req.setAttribute("board", board);
		req.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(req, resp);
		
	}
	
	
}

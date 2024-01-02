package controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Board;
import domain.Criteria;
import domain.Member;
import service.BoardService;
import util.WebUtils;
@WebServlet("/board/modify")
public class BoardModify extends HttpServlet{
	private BoardService boardService = new BoardService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 본인글, 본인여부 확인 
		// 글.id, 
		
		Member member = WebUtils.getMember(req);
		if(member != null) {
			String bno = req.getParameter("bno");
			if(bno != null) {
				Board board = boardService.findBy(Long.valueOf(bno));
				if(board.getId().equals(member.getId()) || member.isAdmin()) {
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
					
					req.setAttribute("cri", criteria);
					
					req.setAttribute("board", board);
					req.getRequestDispatcher("/WEB-INF/views/board/modify.jsp").forward(req, resp);
				}
			}
		}
		WebUtils.alert(resp, "비정상적 접근입니다.", req.getContextPath() + "/board/list");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String bno = req.getParameter("bno");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
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
		
		
		Board board = Board.builder().title(title).content(content).bno(Long.valueOf(bno)).category(criteria.getCategory()).build();
//		System.out.println(board);
		
		boardService.modify(board);
		
		resp.sendRedirect("view?bno=" + bno + "&" + criteria.getQs());
	}

}

package controller.board;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import domain.Attach;
import domain.Board;
import domain.Criteria;
import service.BoardService;
import util.UploadUtils;
import util.WebUtils;
@WebServlet("/board/write")
public class BoardWrite extends HttpServlet{
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
		
		if(WebUtils.getMember(req) == null) {
			// 비 로그인
			// 로그인으로 보내기 
			WebUtils.alert(resp, "로그인 후 사용할 수 있습니다. ", req.getContextPath() 
					+ "/member/signin?href="+URLEncoder.encode(("/board/write?"+criteria.getQs()), "utf-8"));
			
			return;
		}
		
		req.setAttribute("cri", criteria);
		req.getRequestDispatcher("/WEB-INF/views/board/write.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ServletFileUpload fileUpload = UploadUtils.init(); // new ServletFileUpload(diskFileItemFactory);
		
		String id = req.getParameter("id");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		String cate = req.getParameter("category");
		String pageNum = req.getParameter("pageNum");
		String amount = req.getParameter("amount");
		
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
	
		
		List<FileItem> items = fileUpload.parseRequest(new ServletRequestContext(req));
		Board.BoardBuilder boardBuilder = Board.builder();
		List<Attach> attachs = new ArrayList<>();
		for(FileItem fi : items) {
			if(fi.isFormField()) {
				if(fi.getFieldName().equals("id")) {
					boardBuilder.id(fi.getString("utf-8"));
				}
				else if(fi.getFieldName().equals("title")) {
					boardBuilder.title(fi.getString("utf-8"));
				}
				else if(fi.getFieldName().equals("content")) {
					boardBuilder.content(fi.getString("utf-8"));
				}
				else if(fi.getFieldName().equals("category")) {
					cb.category(Integer.parseInt(fi.getString("utf-8")));
				}
				else if(fi.getFieldName().equals("pageNum")) {
					cb.pageNum(Integer.parseInt(fi.getString("utf-8")));
				}
				else if(fi.getFieldName().equals("amount")) {
					cb.amount(Integer.parseInt(fi.getString("utf-8")));
				}
			}
				
				
				
//			}
				
//				System.out.printf("파라미터 명 : %s, 파라미터 값 : %s \n", fi.getFieldName(), fi.getString("utf-8"));
//			} 
			else {
				if(fi.getSize() == 0) continue;
				System.out.printf("파라미터 명 : %s, 파일명 : %s, 파일 크기 : %d \n", fi.getFieldName(), fi.getName(), fi.getSize());
				try {
					/* fi.write(new File("c:/upload", fi.getName())); */
					String origin = fi.getName();
					String ext = "";
					try {
						ext = origin.substring(origin.lastIndexOf("."));
					} catch (StringIndexOutOfBoundsException e) {
					}
					if(origin.lastIndexOf(".") != -1){
						ext = origin.substring(origin.lastIndexOf("."));
					};
					String uuid = UUID.randomUUID() + ext;
					String todayPath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
					File path = new File(UploadUtils.REPOSTITORY, todayPath);
					if(!path.exists()) {
						path.mkdirs();
					}
					fi.write(new File(path, uuid));
					attachs.add(Attach.builder().uuid(uuid).origin(origin).path(todayPath).build());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		Criteria criteria = cb.build();
		Board board = boardBuilder.category(criteria.getCategory()).attachs(attachs).build();
//		System.out.println(board);
		
		boardService.register(board);
		
		resp.sendRedirect("list?" + criteria.getQs());
	}
}
	


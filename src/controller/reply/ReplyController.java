package controller.reply;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.ReplyDao;
import domain.Reply;
import service.ReplyService;

// controller, rest controller
@WebServlet("/reply/*")
public class ReplyController extends HttpServlet{
	private ReplyService replyService = new ReplyService();
	private Gson gson = new Gson();

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// in : rno 
		
		// out : ? status / String / number
		
		String uri = req.getRequestURI();
		String[] arr = uri.substring(1).split("/");
		if(arr.length == 2) { // 단일 조회로 삭제
			Long rno = Long.valueOf(arr[1]);
			System.out.println("단일 삭제, rno : " + rno);
			if(replyService.remove(rno) > 0) {
				resp.setStatus(200);
				resp.getWriter().print("success");
			}else {
				resp.setStatus(500);
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// in : bno 
		
		// out : list
		
		// reply/list/1
		
		// http://localhost:8080/reply/list/{bno}
		String uri = req.getRequestURI();  // /reply/list/{bno}
		String[] arr = uri.substring(1).split("/");  // {"reply", "list", {bno}}
		
		Object o = null;
		if(arr.length == 2) { // 단일 조회
			Long rno = Long.valueOf(arr[1]);
			System.out.println("단일 조회, rno : " + rno);
			o = replyService.get(rno);
		}
		// /reply/list/{bno}
		// /reply/list/{bno}/{lastRno}
		// /reply/cnt/{bno}
		else if (arr.length >= 3) { // 목록 조회
			Long bno = Long.valueOf(arr[2]);
			if(arr[1].equals("list")) {
				int amount = 5;
				Long lastRno = Long.MAX_VALUE;
				if(arr.length > 3) {
					lastRno = Long.valueOf(arr[3]);
				}else {
					
				}
				System.out.println("목록 조회, bno : " + bno);
				
				o = replyService.getList(bno, amount, lastRno);
			}
			else if(arr[1].equals("cnt")) {
				System.out.println("갯수 조회, bno : " + bno);
				
				o = replyService.getCount(bno);
			}
		}
		resp.setContentType("application/json; charset=utf-8");
		resp.getWriter().print(gson.toJson(o));
		
		
		// -----------------------
		
		// in : rno
		
		// out : reply
		
		// reply/10
		// 단일 조회
		// http://localhost:8080/reply/3
		// 전체 조회
		// http://localhost:8080/reply/list/374
//		System.out.println("doGet()");
		// 상대경로
//		System.out.println(req.getRequestURI());
		// 절대경로
//		System.out.println(req.getRequestURL());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// in : reply
		// out : ? status / String / number
		// BufferedReader : 동기화 되기 때문에 멀티 쓰레드 환경에서 안전
		// Scanner & BufferedReader
		// Scanner : 띄어쓰기와 개행문자를 경계로 하여 입력 값을 인식, 가공을 많이 하지 않아도 됨
		// BufferedReader : 개행문자만 경계로 인식하고 입력받은 데이터가 String으로 고정, 가공을 많이 해야함, 대신 빠름
		
		String str = new BufferedReader(new InputStreamReader(req.getInputStream())).lines().collect(Collectors.joining());
		Reply reply = gson.fromJson(str, Reply.class);
		if(replyService.register(reply) > 0) {
			resp.setStatus(200);
			resp.getWriter().print(reply.getRno());
		}else {
			resp.setStatus(500);
		}
		
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// in : reply
		
		// out : ? status / String / number
		String str = new BufferedReader(new InputStreamReader(req.getInputStream())).lines().collect(Collectors.joining());
		Reply reply = gson.fromJson(str, Reply.class);
		if(replyService.modify(reply) > 0) {
			resp.setStatus(200);
			resp.getWriter().print("success");
		}else {
			resp.setStatus(500);
		}
	}
	
	
	
	// 등록(post)
	
	// 삭제(delete)
	
	// 조회(목록) (get) 
	
}

package controller.board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

@WebServlet("/board/attach")
public class BoardAttach extends HttpServlet{
	DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	String REPOSTITORY = "c:/upload";
	String CHARSET = "utf-8";
	int INIT_SIZE_THRESHOLD = 1024 * 1024;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/fileupload.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		File file = new File(REPOSTITORY);
		diskFileItemFactory.setRepository(file);
		diskFileItemFactory.setDefaultCharset(CHARSET);
		diskFileItemFactory.setSizeThreshold(INIT_SIZE_THRESHOLD);
		
		ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
		
		// isFormField : 요청 파라미터가 파일일 경우 false 리턴, 일반 데이터일 경우 true 리턴
		List<FileItem> items = fileUpload.parseRequest(new ServletRequestContext(req));
		for(FileItem fi : items) {
			if(fi.isFormField()) {
				System.out.printf("파라미터 명 : %s, 파라미터 값 : %s \n", fi.getFieldName(), fi.getString("utf-8"));
			} 
			else {
				System.out.printf("파라미터 명 : %s, 파일명 : %s, 파일 크기 : %d \n", fi.getFieldName(), fi.getName(), fi.getSize());
				try {
					/* fi.write(new File("c:/upload", fi.getName())); */
					String origin = fi.getName();
					String ext = "";
//					try {
//						ext = origin.substring(origin.lastIndexOf("."));
//					} catch (StringIndexOutOfBoundsException e) {
//					}
					if(origin.lastIndexOf(".") != -1){
						ext = origin.substring(origin.lastIndexOf("."));
					};
					String uuid = UUID.randomUUID() + ext;
					System.out.println(origin);
					System.out.println(uuid);
					String todayPath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
					File path = new File(file, todayPath);
					if(!path.exists()) {
						path.mkdirs();
					}
					fi.write(new File(path, uuid));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// 파일의 원본 이름, 파일의 변경된 이름, 게시글 번호, 날짜 경로 
		// abcdeabcde.txt
		
		// 첨부파일 등록, 삭제, 수정 (순서)
	}
	
}

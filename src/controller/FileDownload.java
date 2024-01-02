package controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.UploadUtils;

@WebServlet("/file")
public class FileDownload extends HttpServlet{

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uuid = req.getParameter("uuid");
		String path = req.getParameter("path");
		String origin = req.getParameter("origin");
		if(uuid == null || path == null) {
			return;
		}
		// BufferedInputStream : 파일을 읽어올때 8192byte의 버퍼를 두고 작업을 하기 때문에 빠름
		// file.length() : return long type length 
		File file = new File(UploadUtils.REPOSTITORY + "/" + path, uuid);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file)); 
		byte[] bytes = new byte[(int) file.length()];
		bis.read(bytes);
		
		resp.setContentType("application/octet-stream");
		resp.setHeader("content-disposition", "attachment; filename=" + new String(origin.getBytes("utf-8"), "iso-8859-1"));
		resp.getOutputStream().write(bytes);
		// 다운로드 강제 : octet-stream
		// 컨텐트 타입 변경 
	}

}

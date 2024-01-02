package util;

import java.io.File;

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class UploadUtils {
	public static String REPOSTITORY = "c:/upload";
	public static String CHARSET = "utf-8";
	public static int INIT_SIZE_THRESHOLD = 1024 * 1024;
	
	private static DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	
	static {
		File file = new File(REPOSTITORY);
		diskFileItemFactory.setRepository(file);
		diskFileItemFactory.setDefaultCharset(CHARSET);
		diskFileItemFactory.setSizeThreshold(INIT_SIZE_THRESHOLD);
	}
	public static ServletFileUpload init() {
		return new ServletFileUpload(diskFileItemFactory);
	}
}

package domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import util.UploadUtils;

@Data
@Builder
public class Attach {
	private String uuid;
	private String origin;
	private String path;
	private Long bno;
	
	public String getQs() {
		List<String> list = new ArrayList<>();
		list.add("uuid="+uuid);
		list.add("origin="+origin);
		list.add("path="+path);
		list.add("bno="+bno);
		
		return String.join("&", list);
	}
	
	public File toFile() {
		return new File(UploadUtils.REPOSTITORY + "/" + path, uuid);
	}
}

package domain;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class Reply {
	private Long rno;
	private String content;
	private String id;
	private long regDate;
	private Long bno;
}

package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class Board {
	/**
	 * bno : 글 번호
	 * title : 제목
	 * content : 글
	 * id : 아이디
	 * regDate : 작성일
	 * updateDate : 수정일
	 * category : 카테고리
	 * 
	 */
	private Long bno;
	private String title;
	private String content;
	private String id;
	private Date regDate;
	private Date updateDate;
	private Integer category;
	@Builder.Default
	private List<Attach> attachs = new ArrayList<>();
	private int replyCnt;
	private boolean attached;
}

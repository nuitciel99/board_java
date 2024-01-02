package domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 역할 : DTO (data transfer object)
@Data
@Builder
public class Criteria {
	// 페이지 번호
	// 페이지 수량
	// 카테고리
	// 검색 조건 : T > Title, C > Content, I > Id
	// 검색어
	@Builder.Default
	private int pageNum = 1;
	@Builder.Default
	private int amount = 10;
	@Builder.Default
	private int category = 1;
	@Builder.Default
	private String type = "";
	@Builder.Default
	private String keyword = "";
	
	// 
	private final TypeDetails[] typeDetails = {
			new TypeDetails("T", "TITLE", "제목"),
			new TypeDetails("C", "CONTENT", "내용"), 
			new TypeDetails("I", "ID", "작성자"), 
	};
	
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	private class TypeDetails {
		String sn;
		String ln;
		String d;
		
	}
	
	/*
	 * join
	 * join(a,b) : a를 기준으로 b를 이어줌
	 */
	public String getQs() {
//		String[] arr1 = {"pageNum"
		List<String> list = new ArrayList<>();
		list.add("pageNum="+pageNum);
		list.add("amount="+amount);
		list.add("category="+category);
		list.add("type="+type);
		list.add("keyword="+keyword);
		
		return String.join("&", list);
	}
	
	public String getQs2() {
//		String[] arr1 = {"pageNum"
		List<String> list = new ArrayList<>();
		list.add("amount="+amount);
		list.add("category="+category);
		list.add("type="+type);
		list.add("keyword="+keyword);
		
		return String.join("&", list);
	}

}

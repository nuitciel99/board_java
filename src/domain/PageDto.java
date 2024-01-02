package domain;

import lombok.Data;

@Data
public class PageDto {
	private final int SHOW_PAGE_COUNT =	10;
	
	private Criteria cri;
	private int total;
	
	private int startPage;
	private int endPage;
	
	private boolean prev;
	private boolean next;
	
	private boolean prevs;
	private boolean nexts;
	
	
	public PageDto(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		
		endPage = (cri.getPageNum() + (SHOW_PAGE_COUNT - 1)) / SHOW_PAGE_COUNT * SHOW_PAGE_COUNT;
		startPage = endPage - (SHOW_PAGE_COUNT - 1);
		
		int realEnd = (total + (cri.getAmount() - 1)) / cri.getAmount();
		if(endPage > realEnd) {
			endPage = realEnd;
		}
		
		prev = cri.getPageNum() > 1 && cri.getPageNum() <= realEnd;
		next = cri.getPageNum() < realEnd;
		
		prevs = startPage > 1;
		nexts = endPage < realEnd;
	}
	
	
	
	public static void main(String[] args) {
//		System.out.println(new PageDto(Criteria.builder().pageNum(5).build(), 123));
		for(int i = 1; i<= 27; i++) {
			
			PageDto page = new PageDto(Criteria.builder().pageNum(i).build(), 259);
			System.out.println(i + ":" + page);
		}
		
		/*
		 * 
		 * 1 5
		 * 2 5
		 * 3 5
		 * 4 5
		 * 5 5
		 * 6 10
		 */
	}
	
	
}

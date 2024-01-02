package service;

import java.util.List;

import dao.AttachDao;
import dao.BoardDao;
import domain.Attach;
import domain.Board;
import domain.Criteria;

public class BoardService {
	private BoardDao boardDao = new BoardDao();
	private AttachDao attachDao = new AttachDao();
	
	// transaction
	// 글 등록
	public int register(Board board	) {
		System.out.println(board); // title, content, id
		int ret = boardDao.insert(board); 
		System.out.println(board); // title, content, id, bno
		
		// 게시글 작성 후 글 번호를 알아야함
		// [attach1(uudi, origin, path, bno=null), attach2]
		board.getAttachs().stream().map(attach -> {
			attach.setBno(board.getBno());
			return attach;
		}).forEach(attach -> attachDao.insert(attach));
		// 첨부파일들의 필드 내의 bno 값을 조회된 글번호로 지정 
		// 첨부파일 insert  
		
		return ret;
	}
	
	// 글 수정
	public int modify(Board board) {
		return boardDao.update(board);
	}
	
	// 글 삭제
	public int remove(Long bno) {
		attachDao.deleteAll(bno);
		return boardDao.delete(bno);
	}
	
	// 글 목록
	public List<Board> list(Criteria criteria){
		return boardDao.selectList(criteria);
	}
	
	// 글 단일 조회
	public Board findBy(Long bno) {
		Board board = boardDao.selectOne(bno);
		board.setAttachs(attachDao.selectList(bno));
		
		return board;
	}
	// 해당 카테고리의 게시글 갯수 반환 >> 카테고리 선택 시 해당 게시글만 조회
	public int getCount(Criteria criteria) {
		return boardDao.getCount(criteria);
	}
	public List<Board> getTop(int count){
		return boardDao.selectTop(count);
	}
}

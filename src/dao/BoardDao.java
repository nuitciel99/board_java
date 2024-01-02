package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import domain.Board;
import domain.Criteria;
import util.DBUtils;

public class BoardDao {
	
	// 글 등록
	public int insert(Board board) {
		Long bno = null;
		try {
			Connection conn = DBUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select nextval(seq_board) from dual");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				bno = rs.getLong(1);
				board.setBno(bno);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int ret = DBUtils.eu("INSERT INTO TBL_BOARD(BNO, TITLE, CONTENT, ID, CATEGORY) VALUES (?, ?, ?, ?, ?)", 
				bno, board.getTitle(), board.getContent(), board.getId(), board.getCategory());
		
		return ret;
	}
	
	// 글 수정
	public int update(Board board) {
		return DBUtils.eu("UPDATE TBL_BOARD SET TITLE = ?, CONTENT = ?, CATEGORY = ?, UPDATEDATE = now() WHERE BNO = ?", 
				board.getTitle(), board.getContent(), board.getCategory(), board.getBno());
	}
	
	// 글 삭제
	public int delete(Long bno) {
		return DBUtils.eu("DELETE FROM TBL_BOARD WHERE BNO = ?", bno);
	}
	
	// 목록 조회
	public List<Board> selectList(Criteria cri) {
		List<Board> list = new ArrayList<Board>();
		StringBuilder sb = new StringBuilder();	// StringBuilder : 여러개 문자열 연결, 메모리사용에 효율적
		
	
		sb.append(" SELECT TB.*,\r\n"); 
		sb.append("	(SELECT COUNT(*) FROM TBL_REPLY TR WHERE BNO =TB.BNO) replycnt,\r\n"); 
		sb.append(" (SELECT COUNT(*) FROM TBL_ATTACH TR WHERE BNO =TB.BNO) attached\r\n"); 
		sb.append(" FROM TBL_BOARD TB \r\n"); 
		sb.append(" WHERE CATEGORY = ?\r\n"); 
		
		/*
		 * T > Title, C > Content, I > Id
		 * join(string)
		 * Keyword와 Type의 길이가 0 이상인 경우 >>> map에 type 담기
		 * Stream.of : 가변 매개변수(variable parameter)를 전달받아 스트림 생성
		 * Stream.map : 원하는 필드만 리턴 or 특정형태로 변환
		 * Stream.collect : 데이터 처리 후 최후에 원하는 형태로 변환
		 * Stream.toList : 리스트 형태로 반환 
		 */
		
		if(cri.getKeyword().trim().length() > 0 && cri.getType().length() > 0) {
			HashMap<String, String>map = new HashMap<String, String>();
			map.put("T", "TITLE");
			map.put("C", "CONTENT");
			map.put("I", "ID");

			String[] keywords = cri.getType().split("");
			sb.append("	AND (\r\n");
			List<String> l = Stream.of(keywords)
			.map(k -> String.format(" LOWER(%s) LIKE LOWER(CONCAT(CONCAT('%%', ?), '%%')) \r\n", map.get(k))) 
			// 람다식으로 문자열 생성 / map에서 가져온 각 컬럼 이름을 이용하여 대소문자 구분 없이 컬럼의 값이 검색어와 일치하는지 확인하는 SQL 쿼리 조건
			.collect(Collectors.toList()); // 수집
			sb.append(String.join("	OR \r\n", l));
			sb.append("	)");
	
		}					
			
		sb.append("order by bno desc\r\n");
		sb.append("limit ?\r\n");
		sb.append("offset ?\r\n");
		
		System.out.println(sb); // 쿼리문 출력
		
		Connection conn = DBUtils.getConnection(); // DB연결
		try {
			PreparedStatement pstmt = conn.prepareStatement(sb.toString()); 
			// PreparedStatement 객체의 pstmt선언 / toString :sb에서 가져와서 문자열로 변환
			// pstmt에 sb.toString()에서 가져온 SQL 쿼리를 실행할 수 있는 PreparedStatement 객체가 저장
			
			int idx = 1;
			pstmt.setInt(idx++, cri.getCategory());
		
			
			if(cri.getKeyword().trim().length() > 0 ) { // trim : 공백제거
				for(int i = 0; i <cri.getType().length(); i ++) {
					pstmt.setString(idx++, cri.getKeyword());
				}
			}
			
			pstmt.setInt(idx++, cri.getAmount());
			pstmt.setInt(idx++, (cri.getPageNum() - 1) * cri.getAmount());
			
			
			ResultSet rs = pstmt.executeQuery(); // executeQuery :  데이터베이스 데이터 읽기위한
			while(rs.next()) {
				list.add(Board.builder()
						.bno(rs.getLong("bno"))
						.title(rs.getString("title"))
						.id(rs.getString("id"))
						.regDate(rs.getDate("regdate"))
						.updateDate(rs.getDate("updatedate"))
						.category(rs.getInt("category"))
						.replyCnt(rs.getInt("replyCnt"))
						.attached(rs.getBoolean("attached"))
						.build());
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	// 글 단일 조회
	public Board selectOne(Long bno) {
		Board board = null;
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TBL_BOARD WHERE BNO = ?");
			pstmt.setLong(1, bno);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				board = Board.builder()
						.bno(rs.getLong("bno"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.id(rs.getString("id"))
						.regDate(rs.getDate("regDate"))
						.updateDate(rs.getDate("updateDate"))
						.category(rs.getInt("category"))
						.build();
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return board;
	}
	
	/*
	 * 카테고리에 포함된 게시글 수 반환
	 */
	public int getCount(Criteria cri) {
        int cnt = 0;
        Connection conn = DBUtils.getConnection();
        try {
            StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM TBL_BOARD WHERE CATEGORY = ?");
            if (cri.getKeyword().trim().length() > 0 && cri.getType().length() > 0) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("T", "TITLE");
                map.put("C", "CONTENT");
                map.put("I", "ID");

                String[] keywords = cri.getType().split("");
                sb.append("AND (\r\n");
                List<String> l = Stream.of(keywords)
                .map(k -> String.format("        LOWER(%s) LIKE LOWER(CONCAT(CONCAT('%%', ?),'%%')) \r\n", map.get(k)))
                .collect(Collectors.toList());
                sb.append(String.join("        OR \r\n", l));

                sb.append(" )");
            }

            PreparedStatement pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, cri.getCategory());
            if (cri.getKeyword().trim().length() > 0) {
                for(int i = 0; i <cri.getType().length(); i++) {
                    pstmt.setString(i+2, cri.getKeyword());
                }
            }
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cnt = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnt;
    }

	public List<Board> selectTop(int count){
		List<Board> list = new ArrayList<>();
		
		String sql = 
		"SELECT * FROM (\r\n" + 
		"	SELECT /*+ INDEX_DESC(TR BOARD_PK) */ \r\n" + 
		"		BNO, TITLE, CATEGORY,\r\n" + 
		"		RANK () OVER(PARTITION BY CATEGORY ORDER BY BNO DESC) R \r\n" + 
		"	FROM TBL_BOARD TB\r\n" + 
		")A \r\n" + 
		"WHERE R <= ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, count);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(Board.builder()
						.bno(rs.getLong("bno"))
						.title(rs.getString("title"))
						.category(rs.getInt("category"))
						.build());
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public static void main(String[] args) throws Exception {
		BoardDao dao = new BoardDao();
//		Board board = Board.builder().title("제목").content("내용").id("hong").build();
//		System.out.println(board);
//		dao.insert(board);
//		System.out.println(board);
//		Criteria criteria = Criteria.builder().category(2).pageNum(2).build(); // 기본값
		Criteria criteria = Criteria.builder().build();
//		dao.selectList(criteria).forEach(System.out::println);
		dao.selectTop(2).forEach(System.out::println);
//				.category(1)
//				.pageNum(1)
//				.type("")
//				.keyword("dao")
//				.build(); // 기본값
//		System.out.println(criteria);
//		dao.selectList(criteria).forEach(System.out::println);
		
//		System.out.println(dao.getCount(criteria));
		
		
//		dao.insert(Board.builder().title("dao test 제목").id("hong").build());
//		dao.insert(Board.builder().title("dao test 제목").content("dao test 내용").id("hong").build());
//		dao.update((Board.builder().title("dao test 제목 수정").content("dao test 내용 수정").bno(4L).build()));
//		dao.delete(2L);
//		dao.selectList().forEach(System.out::println);
//		dao.selectOne(1L);
		
	}
}

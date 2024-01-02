package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Attach;
import domain.Board;
import domain.Criteria;
import domain.Reply;
import util.DBUtils;

public class ReplyDao {
	// 목록 조회
	public List<Reply> selectList(Long bno, int amount, Long lastRno){
		List<Reply> list = new ArrayList<>();
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from tbl_reply where bno = ? and rno < ? order by rno desc limit ?");
			pstmt.setLong(1, bno);
			pstmt.setLong(2, lastRno);
			pstmt.setInt(3, amount);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(Reply.builder()
						.rno(rs.getLong("rno"))
						.content(rs.getString("content"))
						.id(rs.getString("id"))
						.regDate(rs.getTimestamp("regDate").getTime())
						.bno(rs.getLong("bno"))
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
	// 단일 조회
	public Reply selectOne(Long rno) {
		Reply reply = null;
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TBL_REPLY WHERE RNO = ?");
			pstmt.setLong(1, rno);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				reply = Reply.builder()
						.rno(rs.getLong("rno"))
						.content(rs.getString("content"))
						.id(rs.getString("id"))
						.regDate(rs.getTimestamp("regDate").getTime())
						.bno(rs.getLong("bno"))
						.build();
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reply;
	}
	
	// 댓글 갯수
	public int selectCount(Long bno) {
        int cnt = 0;
        Connection conn = DBUtils.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM TBL_REPLY TR WHERE BNO = ?");
            pstmt.setLong(1, bno);
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
	
	// 수정
	public int update(Reply reply) {
		return DBUtils.eu("UPDATE TBL_REPLY SET CONTENT = ? WHERE RNO = ?", 
				reply.getContent(), reply.getRno());
	}
	// 삭제
	public int delete(Long rno) {
		return DBUtils.eu("DELETE FROM TBL_REPLY WHERE RNO = ?", rno);
	}
	
	// 작성
	public int insert(Reply reply) {
		Long rno = null;
		try {
			Connection conn = DBUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT NEXTVAL(SEQ_REPLY) FROM DUAL");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				rno = rs.getLong(1);
				reply.setRno(rno);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return DBUtils.eu("INSERT INTO TBL_REPLY(RNO, CONTENT, ID, BNO) VALUES (?,?,?,?)", 
				reply.getRno(), reply.getContent(), reply.getId(), reply.getBno());
	}
	public static void main(String[] args) {
		ReplyDao dao = new ReplyDao();
//		dao.insert(Reply.builder().bno(374L).content("시각 테스트").id("park").build());
//		dao.insert(Reply.builder().content("댓글").id("kim").bno(374L).build());
//		
		BoardDao boardDao = new BoardDao();
//		List<Board> boardlist = boardDao.selectList(Criteria.builder().build());
//		int cnt = 0;
//		for(int i = 0; i<boardlist.size(); i++) {
//			for (int j = 0; j < 5; j++) {
//				dao.insert(Reply.builder().content("댓글" + cnt++).id("kim").bno(boardlist.get(i).getBno()).build());
//			}
//		}
//		dao.selectCount(374L);
		dao.selectList(374L, 6, 68L).forEach(System.out::println);
		
		
		
	}
	
}

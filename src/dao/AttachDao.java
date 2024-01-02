package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Attach;
import domain.Category;
import util.DBUtils;

public class AttachDao {
	// insert
	public int insert(Attach attach	) {
		return DBUtils.eu("INSERT INTO TBL_ATTACH VALUES(?,?,?,?)", 
				attach.getUuid(), attach.getOrigin(), attach.getPath(), attach.getBno());
	}

	public List<Attach> selectList(Long bno){
		List<Attach> list = new ArrayList<>();
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TBL_ATTACH WHERE BNO = ?");
			pstmt.setLong(1, bno);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(Attach.builder()
						.uuid(rs.getString("uuid"))
						.origin(rs.getString("origin"))
						.path(rs.getString("path"))
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
	
	// Attach selectOne(uuid)
	
	// delete(uuid) - 단일 삭제
	public int delete(String uuid) {
		return DBUtils.eu("DELETE FROM TBL_ATTACH WHERE UUID = ?", uuid);
	}
	
	// deleteAll(bno) - 번호로 삭제 
	public int deleteAll(Long bno) {
		return DBUtils.eu("DELETE FROM TBL_ATTACH WHERE BNO = ?", bno);
	}
	
	public static void main(String[] args) {
		AttachDao dao = new AttachDao();
		dao.selectList(451L).forEach(System.out::println);
	}
}

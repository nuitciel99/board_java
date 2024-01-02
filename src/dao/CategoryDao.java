package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Category;
import util.DBUtils;

public class CategoryDao {
	
	public List<Category> selectList(){
		List<Category> list = new ArrayList<Category>();
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM TBL_CATEGORY ORDER BY  1");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(Category.builder()
						.cno(rs.getInt("cno"))
						.cname(rs.getString("cname"))
						.cdesc(rs.getString("cdesc"))
						.regDate(rs.getDate("regDate"))
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
	
	public static void main(String[] args) {
//		CategoryDao dao = new CategoryDao();
//		dao.selectList().forEach(System.out::println);
		System.out.println(new CategoryDao().selectList().stream().filter(s->s.getCname().equals("free")).findFirst().get().getCdesc());
	}

}

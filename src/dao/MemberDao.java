package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Member;
import util.DBUtils;
// 1. insert, update, delete
// 2. select
public class MemberDao {
	
	public int insert(Member member) {
//		int ret = 0;
		return DBUtils.eu("INSERT INTO TBL_MEMBER (ID, PWD, NAME, EMAIL) VALUES (?, ?, ?, ?)", member.getId(), member.getPwd(), member.getName(), member.getEmail());
		
//		try {
////			Statement stmt = conn.createStatement();
////			ret = stmt.executeUpdate("INSERT INTO TBL_MEMBER (ID, PWD, NAME) VALUES ('"
////					+ "" + member.getId() + "', '" + member.getPwd() + "', '" + member.getName() + "')");
//			// PreparedStatement를 활용하면 지시자를 통한 대체 문자열, 타입, 기본적인 escape 처리 지원
//			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TBL_MEMBER (ID, PWD, NAME) VALUES (?, ?, ?)");
//			pstmt.setString(1, member.getId());
//			pstmt.setString(2, member.getPwd());
//			pstmt.setString(3, member.getName());
//			
//			pstmt.executeUpdate(); 
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return ret;
	}
	
	public int delete(String id) {
		return DBUtils.eu("DELETE TBL_MEMBER WHERE ID = ?", id);
	}
	
	public int update(Member member) {
		return DBUtils.eu("UPDATE TBL_MEMBER SET NAME = ?, EMAIL = ? WHERE ID = ?", member.getName(), member.getEmail(), member.getId());
	}
	public int updatePwd(Member member) {
		return DBUtils.eu("UPDATE TBL_MEMBER SET PWD = ? WHERE ID = ?", member.getPwd(), member.getId());
	}
	// select member from DB by id, used for login
	public Member selectOne(String id) {
		Member member = null;
		Connection conn = DBUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM TBL_MEMBER WHERE ID = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int idx = 1;
				member = Member.builder()
						.id(rs.getString(idx++))
						.pwd(rs.getString(idx++))
						.name(rs.getString(idx++))
						.regDate(rs.getDate(idx++))
						.email(rs.getString(idx++))
						.admin(rs.getBoolean(idx++))
						.build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member;
	}
	
	public static void main(String[] args) {
//		new MemberDao().insert(Member.builder().id("park").pwd("1234").name("박길동").build());
//		new MemberDao().delete("hong");
//		new MemberDao().update(Member.builder().id("park").pwd("5678").name("박길동2").build());
		System.out.println(new MemberDao().selectOne("kim"));
	}
}

package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBUtils {
	public static Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe", "board", "1234");
			conn = DriverManager.getConnection("jdbc:mariadb://jykjy.co.kr:3306/board_servlet", "board", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	// execution method
	public static int eu(String sql, Object... args) { // 객체 지향 수식
        int ret = 0;
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for(int i = 1; i <= args.length; i++) {
                setPstmtParameter(pstmt, i, args[i-1]); // 3번 파라메터. 어떤 스트링 배열이 올 것인데 그것의 0번 인덱스가 갖고 있는 값부터 순차적으로
                // setPstmPrammeter 라는 메서드를 새로 만들어 pstmt 에 들어올 타입을 스위치 문을 통해 필터링 하는 구간이 생겼다.
            }
            ret = pstmt.executeUpdate(); 
            // 무언가를 반환하게 되는데. 무엇을 반환 하느냐.
            // 첫번째는 DB 에서의 row(s) 카운트가 나온다. 0개가 나온면 아무것도 안하게 된다.
            // SELECT 시 나오는 카운터..
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        pstmt.setString(1, member.getId());
//        pstmt.setString(2, member.getPwd());
//        pstmt.setString(3, member.getName());

        return ret;  // 행 갯수를 알아오고. 
    }

	
	public static void setPstmtParameter(PreparedStatement pstmt, int idx, Object o) throws SQLException{
		if(o == null) {
			pstmt.setObject(idx, null);
			return;
		}
		
		switch(o.getClass().getSimpleName()){
		case "String":
			pstmt.setString(idx, (String)o);
			break;
		
		case "int": case "Integer":
			pstmt.setInt(idx, (Integer)o);
			break;
		
		case "long": case "Long":
			pstmt.setLong(idx, (Long)o);
			break;
		case "double": case "Double":
			pstmt.setDouble(idx, (Double)o);
			break;
		case "Date":
			pstmt.setDate(idx, (Date)o);
			break;
		}
	}
}

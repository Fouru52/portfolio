package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.dbcp.BasicDataSource;

public class db_insert {
	Connection con = null;
	PreparedStatement ps = null;
	int result = 0; 
	
	
	public db_insert(BasicDataSource dataSource) {
		try {
			this.con = dataSource.getConnection();
		}
		catch(Exception e) {
			System.out.println("데이터베이스 접속 오류!");
		}
	}
	
	// 관리자 추가
	public int admin_insert(String password, join_dto jd) {
		try {
			String sql = "insert into admin values('0',?,?,?,?,?,?,?,?,?,?,0,now())";
			this.ps = this.con.prepareStatement(sql);
			
			this.ps.setString(1, jd.getAid());
			this.ps.setString(2, password);
			this.ps.setString(3, jd.getAnm());
			this.ps.setString(4, jd.getAemail());
			this.ps.setString(5, jd.getAtel());
			this.ps.setString(6, jd.getAtel2());
			this.ps.setString(7, jd.getAtel3());
			this.ps.setString(8, jd.getAdepartment());
			this.ps.setString(9, jd.getAposition());
			this.ps.setString(10, jd.getAok());
			
			this.result = this.ps.executeUpdate();
			
			System.out.println(this.result);
			this.ps.close();
			this.con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Database 문법 오류! 22");
		}
		return this.result;
	}
}

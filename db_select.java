package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbcp.BasicDataSource;

public class db_select {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String result = null;	
	
	public db_select(BasicDataSource dataSource) {
		try {
			this.con =dataSource.getConnection();
		}
		catch(Exception e) {
			System.out.println("데이터베이스 접속 오류!");
		}
	}
	

	public String admincheck(String aid, String apass) {
		security sc = new security(apass);
		String pass = sc.md5_se();
		String sql = "select * from admin where aid=?";
		try {
		this.ps = this.con.prepareStatement(sql);
		this.ps.setString(1, aid);
		
		this.rs = this.ps.executeQuery();
		while(this.rs.next()) {
			if(aid.equals(this.rs.getString("aid"))) {
				if(pass.equals(this.rs.getString("apass"))) {
					this.result = this.rs.getString("aid")+","+this.rs.getString("anm")+","+this.rs.getString("aok");
				}
			}
		}
		this.rs.close();
		this.ps.close();
		this.con.close();

		}
		catch(Exception e) {
			System.out.println("sql 문법 오류 발생!");
		}
		
		return this.result;
	}
}

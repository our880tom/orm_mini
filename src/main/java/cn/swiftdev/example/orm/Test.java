package cn.swiftdev.example.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static List<Member> select(String sql){
    	List<Member> result = new ArrayList<Member>();
    	
    	Connection conn = null;
    	PreparedStatement pstm = null;
    	ResultSet rs = null;
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/blog", "root", "123456");
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery(sql);
			while(rs.next()){
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				member.setAge(rs.getInt("age"));
				member.setAddress(rs.getString("address"));
				result.add(member);
			}
						
		} catch (ClassNotFoundException|SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(pstm != null){
					pstm.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	return result;
    }
    
    public static void main(String[] args) {
		List<Member> members = Test.select("select * from member");
		for(Member member: members){
			System.out.println(member);
		}
	}
}

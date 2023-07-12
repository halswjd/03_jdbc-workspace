package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.common.PhoneTemplate;
import com.kh.model.vo.Phone;

public class PhoneDao {
	
	public int insertPhone(Connection conn, Phone p) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO PHONE VALUES(SEQ_NO.NEXTVAL, ?, ?, ?, ?)";
//		INSERT INTO PHONE VALUES(SEQ_NO.NEXTVAL, '조비', 8, '경남 거제시', '01055552222');
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getName());
			pstmt.setInt(2, p.getAge());
			pstmt.setString(3, p.getAddress());
			pstmt.setString(4, p.getPhone());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			PhoneTemplate.close(pstmt);
		}
		
		return result;
		
	}//
	
	public ArrayList<Phone> selectPhone(Connection conn) {
		
		ArrayList<Phone> list = new ArrayList<Phone>();
		
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		
		String sql = "SELECT * FROM PHONE";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Phone(rset.getInt("NO"), rset.getString("NAME"), rset.getInt("AGE"), rset.getString("ADDRESS"	), rset.getString("PHONE")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			PhoneTemplate.close(rset);
			PhoneTemplate.close(pstmt);
		}
		
		return list;
		
	}
	
}

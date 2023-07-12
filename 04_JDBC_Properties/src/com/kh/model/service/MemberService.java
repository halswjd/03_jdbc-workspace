package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*; // 이렇게 하면 JDBCTemplate에 있는 모든 Static메소드 그냥(메소드명으로만) 호출 가능
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberService {
	
	public int insertMember(Member m) {
		
		// 1) jdbc driver 등록
		// 2) Connection 객체 생성
		Connection conn = /*JDBCTemplate.*/getConnection();
		int result = new MemberDao().insertMember(conn, m); // conn 은 왜 넘겨? pstmt 만들려면 conn 필요함
		
		// 6) 트랙제션 처리
		if(result > 0) {
			/*JDBCTemplate.*/commit(conn);
		}else {
			/*JDBCTemplate.*/rollback(conn);
		}
		
		// 7) 다 쓴 자원 반납처리
		/*JDBCTemplate.*/close(conn);
		
		return result;
	}
	
	public ArrayList<Member> selectList(){
		Connection conn = getConnection();
		ArrayList<Member> list = new MemberDao().selectList(conn);
		
		close(conn);
		
		return list;
	}
	
	public Member selectByUserId(String userId) {
		
		Connection conn = getConnection();
		Member m = new MemberDao().selectByUserId(conn, userId);
		
		close(conn);
		
		return m;
	}
	
	public ArrayList<Member> selectByUserName(String keyword) {
		
		Connection conn = getConnection();
		ArrayList<Member> list = new MemberDao().selectByUserName(conn, keyword);
		
		close(conn);
		
		return list;
	}
	
	public int updateMember(Member m) {
		
		Connection conn = getConnection();
		
		int result = new MemberDao().updateMember(conn,m);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result;
	}
	
	public int deleteMember(String deleteId) {
		
		Connection conn = getConnection();
		
		int result = new MemberDao().deleteMember(conn,deleteId);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result;
		
	}
}

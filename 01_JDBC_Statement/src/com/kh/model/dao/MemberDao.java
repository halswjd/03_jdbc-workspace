package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

// DAO (Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 SQL문 실행 후 결과받기 (=> JDBC 작업)
//							  결과를 Controller로 다시 리턴

public class MemberDao {
	/*
	 *  * JDBC용 객체
	 *  - Connection : DB의 연결정보를 담고있는 객체 
	 *  - [Prepared]Statement : 연결된 DB에 SQL문 전달해서 실행하고 그 결과를 받아내는 객체 ★★★
	 *  - ResultSet : SELECT문 실행 후 조화된 결과물들이 담겨있는 객체
	 *  
	 *  * JDBC 과정 (순서중요!)
	 *  1) JDBC driver 등록
	 *  2) Connection 객체 생성
	 *  3) Statement 객체 생성
	 *  4) SQL문 전달하면서 실행 (executeQuery, executeUpdate)
	 *  5) 결과받기
	 *  	> select문 => ResultSet 객체 (조회된 데이터들이 담겨있음) => 6_1
	 *  	> dml문 => int (처리된 행 수) => 6_2
	 *  6_1) ResultSet에 담겨있는 데이터들을 하나씩 하나씩 vo객체에 주섬주섬 옮겨 담기
	 *  6_2) 트랜젝션 처리
	 *  7) 다 사용한 JDBC 객체 반드시 자원 반납!(생성된 역순으로), 안하면 디비 락 걸림!
	 *  
	 */
	
	/**
	 * 사용자가 입력한 정보들을 추가시켜주는 메소드
	 * @param m : 사용자가 입력한 값들이 주섬주섬 담겨있는 Member 객체
	 */
	public int insertMember(Member m) {
		// insert문 => 처리된 행수(int) => 트랜젝션 처리
		
		// 필요한 변수들 먼저 셋팅
		int result = 0; // 처리된 결과(처리된 행수)를 받아 줄 변수
		Connection conn = null; // 연결된 DB의 연결정보를 담는 객체
		Statement stmt = null; // "완성된 sql(실제 값이 다 채워진 상태로)"문 전달해서 곧바로 실행 후 결과 받는 객체
		
		// 실행할 SQL문 (반드시 완성된 형태로 만들어두기!!!)
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXX', 'XXX', 'X', XX, 'XXX', 'XXX', 'XXX', 'XXX', SYSDATE)
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, "
				+ "'" + m.getUserId() + "',"
				+ "'" + m.getUserPwd() + "',"
				+ "'" + m.getUserName() + "',"
				+ "'" + m.getGender() + "',"
					  + m.getAge() + ","
				+ "'" + m.getEmail() + "'," 
				+ "'" + m.getPhone() + "'," 
				+ "'" + m.getAddress() + "'," 
				+ "'" + m.getHobby() + "', SYSDATE)";
		
//		System.out.println(sql); // 콘솔에 출력된 쿼리문 복사해서 문법 맞는지 확인!
		
		try {
			// 1) JDBC 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성 == db에 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4,5) sql문 전달하면서 실행 후 결과받기
			result = stmt.executeUpdate(sql);
			
			// 6) 트랜젝션 처리
			if(result > 0) { // 성공시 커밋
				conn.commit();
			} else { // 실패시 롤백
				conn.rollback();
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7) 생성된 역순으로 다 쓴 자원 반납
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}//
	
	/**
	 * 사용자가 요청한 회원 전체 조회를 처리해주는 메소드
	 * @return 조회된 결과가 있으면 조회된 결과들이 담겨있는 list | 조회된 결과가 없으면 텅 빈 list
	 */
	public ArrayList<Member> selectList() {
		// select문(여러행 조회) => ResultSet 객체 반환 받음 => ArrayList에 차곡차곡 담기
		// 결과들의 각 한행들을 Member 객체로 다 받고 => ArrayList
		
		// 필요한 변수들 셋팅
		ArrayList<Member> list = new ArrayList<Member>(); // [] 현재까지 텅 비어있는 상태
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; // select문 실행시 조호된 결과값들이 최초로 담기는 객체
		
		// 실행할 sql문
		String sql = "SELECT * FROM MEMBER";
		
		try {
			// 1) jdbc 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statment 생성
			stmt = conn.createStatement();
			
			// 4,5) sql실행 및 결과받기
			rset = stmt.executeQuery(sql);
			
			// 6) ResultSet으로 부터 데이터 하나씩 뽑아서 vo객체에 주섬주섬 담고 list에 vo객체 추가
			while(rset.next()) {
				
				// 현재 rset의 커서가 가리키고 있는 한 행의 데이터들을 싹다 뽑아서 Member 객체에 주섬주섬 담기
				Member m = new Member();
				m.setUserNo(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				
				// 현재 참조(커서)하고 있는 행에 모든 컬럼에 대한 데이터들을 한 Member 객체에 담기 끝!
				// => list에 추가
				list.add(m); // list에 해당 회원 객체 차곡차곡 담기
			}
			
			// 반복문이 다 끝난 시점에
			// 만약에 조회된 데이터가 없었다면 list가 텅텅 비어있는 상태
			// 만약에 조회된 데이터가 있엇다면 list에 뭐라도 담겨 있을 것!
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7) 다 쓴 자원 반납
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return list; // 텅빈리스트 | 뭐라도 담겨있는 리스트
	}//
	
	/**
	 * 사용자의 아이디로 회원검색 요청 처리해주는 메소드
	 * @param userId	사용자가 입력한 검색하고자 하는 회원 아이디값
	 * @return 검색된 결과가 있으면 생성된 Member객체 | 결과가 없으면 null
	 */
	public Member selectByUserId(String userId) {
		// 실행할 쿼리문 == select문 => 결과는 한행 (userId는 unique 제약조건있음) => ResultSet 객체
		// 그럼 굳이 ArrayList 필요 없음! 멤버 객체 한개만 있으면 될 듯
		
		// 필요한 변수들 셋팅
		Member m = null; // 바로 new로 객체 생성하지 않는 이유 => 조회 결과가 있을수도있고 없을수도 있어서
		
		// JDBC 객체 셋팅
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = '" + userId + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) { // 한 행이라도 조회됐을 때
				// 조회 잘 됐으면 해당 조회된 컬럼값들을 쏙쏙 뽑아서 하나의 Member 객체의 각 필드에 담기
				m = new Member(rset.getInt("USERNO"), 
								rset.getString("USERID"), 
								rset.getString("USERPWD"), 
								rset.getString("USERNAME"), 
								rset.getString("GENDER"), 
								rset.getInt("AGE"), 
								rset.getString("EMAIL"), 
								rset.getString("PHONE"), 
								rset.getString("ADDRESS"), 
								rset.getString("HOBBY"), 
								rset.getDate("ENROLLDATE"));
			}
			// 위의 조건문 다 끝난 시점에
			// 만약에 조회된 데이터가 없었을 경우 => m은 null
			// 만약에 조회된 데이터가 있을 경우 => m은 생성 후 뭐라도 담겨있음
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
		}
		return m;
	}//
	
	public ArrayList<Member> selectByUserName(String userName) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4,5) sql문 전달해서 실행 후 결과 받기
			rset = stmt.executeQuery("SELECT * FROM MEMBER WHERE USERNAME LIKE '%" + userName + "%'");
			
			
			while(rset.next()) {
				Member m = new Member(rset.getInt("USERNO"), 
								rset.getString("USERID"), 
								rset.getString("USERPWD"), 
								rset.getString("USERNAME"), 
								rset.getString("GENDER"), 
								rset.getInt("AGE"), 
								rset.getString("EMAIL"), 
								rset.getString("PHONE"), 
								rset.getString("ADDRESS"), 
								rset.getString("HOBBY"), 
								rset.getDate("ENROLLDATE"));
				
				list.add(m);
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public int deleteMember(String deleteId) {
		
		Member m = null;
		
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate("DELETE FROM MEMBER WHERE USERID = '" + deleteId + "'");
			
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
		
	}
	
	
}

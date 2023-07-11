package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestRun {

	public static void main(String[] args) {

		/*
		 * 	* JDBC용 객체
		 *  - Connection : DB의 연결정보를 담고있는 객체
		 *  - [Prepared]Statement : 연결된 DB에 SQL문 전달해서 실행하고 그 결과를 받아내는 객체
		 *  - ResultSet : SELECT문 실행 후 조회된 결과물들이 담겨있는 객체
		 *  
		 *  * JDBC 과정 (순서중요!)
		 *  1) jdbc driver 등록 : 해당 DBMS(오라클)가 제공하는 클래스 등록
		 *  2) Connection 생성 : 연결하고자 하는 DB정보를 입력해서 해당 DB와 연결하면서 생성
		 *  3) Statement 생성 : Connection 객체를 이용해서 생성 (sql문 실행 및 결과받는 객체)
		 *  4) sql문 전달하면서 실행 : Statement 객체를 이용해서 sql문 실행
		 *  5) 결과받기
		 *  	> SELECT문 실행 => ResultSet 객체 (조회된 데이터들이 담겨 있음) => 6_1
		 *  	> 	 DML문 실행 => int (처리된 행 수) => 6_1
		 *  
		 *  6_1) ResultSet에 담겨있는 데이터들을 하나씩 하나씩 뽑아서 vo객체에 주섬주섬 옮겨 담기 [+ 여러행 조회시에는 ArrayList에 차곡차곡 담기]
		 *  6_2) 트랜젝션 처리 (성공적으로 수행했으면 commit, 실패했으면 rollback)
		 *  
		 *   7) 다 사용한 JDBC객체를 반드시 자원 반납!! 안하면 DB 락걸림!! (close) => 생성된 역순으로!
		 *  
		 */
		
		/*
		
		// 1. 각자 pc(localhost)에 JDBC계정에 연결 한 후 TEST테이블에 INSERT 해보기
		// INSERT문 => 처리된 행 수 (int) => 트랜젝션 처리
		
		Scanner sc = new Scanner(System.in);
		System.out.print("번호 : ");
		int num = sc.nextInt();
		
		sc.nextLine(); // 버퍼에 남아있는 엔터 제거
		
		System.out.print("이름 : ");
		String name = sc.nextLine();
		
		
		
		
		// 필요한 변수들 먼저 셋팅
		int result = 0; 			// 결과(처리된 행수)를 받아줄 변수
		Connection conn = null;		// DB의 연결정보를 보관할 객체
		Statement stmt = null;		// sql문 전달해서 실행 후 결과 받는 객체
		
		// 앞으로 실행할 sql문("완성형태"로 만들어두기) (쿼리문 맨 뒤에 세미콜론 없어야됨!!!)
//		String sql = "INSERT INTO TEST VALUES(1, '차은우', SYSDATE)";
		String sql = "INSERT INTO TEST VALUES(" + num + ", '" + name + "', SYSDATE)";
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver"); // ojdbc.jar 파일 추가 안했을 경우 | 추가는 했는데 오타가 나서 클래스를 못찾는경우
			System.out.println("jdbc driver 등록 성공");
			
			// OracleDriver 이건 자바에서 제공하는 클래스 아님... 패키지 보면 oracle로 시작함
			// 그럼 어떻게 써야하지? => ojdbc6.jar 파일을 등록해줘야함!
			
			// 2) Connection 객체 생성 : DB에 연결 (url, 계정명, 비번)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement(); // conn을 이용해 stmt를 생성하기때문에 "생성된 역순"으로 닫아야함
			
			// 4, 5) sql문 전달하면서 실행 후 결과받기 (처리된 행수)
			result = stmt.executeUpdate(sql);
			// 내가 실행할 sql문이 DML(INSERT, UPDATE, DELETE)문일 경우 => stmt.executeUpdate("DML문") : int
			// 내가 실행할 sql문이 SELECT문 일 경우 => stmt.executeQuery("SELECT문") : ResultSet
			
			// 6) 트랙젝션 처리
			if(result > 0) { // 성공했을 경우 commit
				conn.commit();
			} else { // 실패했을 경우 rollback
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("OracleDriver클래스를 찾지 못했습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
	
			e.printStackTrace();
		} finally {
			try {
				// 7) 생성된 역순으로 반납
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (result > 0) {
				System.out.println("insert 성공!!");
			} else {
				System.out.println("insert 실패 ㅠㅠ");
			}
		}
		
		*/
		
		// 2. 내 pc에 DB상에 JDBC계정에 TEST테이블에 있는 모든 데이터 조회해보기
		// SELECT문 => 결과 ResultSet(조회된 데이터들 담겨있음) 받기 => ResultSet으로 부터 데이터 뽑기
		
		// 필요햔 변수들 셋팅
		// 왜 변수를 먼저 셋팅하나요? -> finally 구문에서 close하기 위해서 전역변수로 셋팅!
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; // select문 실행하여 조회된 결과값들이 처음에 실직적으로 담길 객체
		
		// 실행할 sql문 (쿼리문 작성시 세미콜론; 하지않기!!! 내부적으로 알아서 SQL문 뒤에 세미콜론 찍어줌)
//		String sql = "SELECT * FROM TEST";
//		String sql = "SELECT * FROM TEST WHERE TESTNO = 1";
		String sql = "SELECT * FROM TEST WHERE TESTNAME LIKE '하%' ";
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4,5) sql문 전달해서 실행 후 결과받기 (ReseultSet 객체)
			rset = stmt.executeQuery(sql);
			
			// rset.next() => 커서를 움직이는 메소드, 반환형 boolean : 다음꺼가 있으면 true, 없으면 false
			
			// 6)
			while(rset.next()) {
				// 현재 참조하는 rset으로 부터 어떤 컬럼에 해당하는 값을 어떤 타입으로 뽑을건지 제시해야됨!
				// DB의 컬럼명 제시!! (대소문자 가리지 않음!)
				int tno = rset.getInt("TESTNO");
				String tname = rset.getString("TESTNAME");
				Date tdate = rset.getDate("TESTDATE"); // 클래스 import시 무조건 sql에 있는걸로!!
				
				System.out.println(tno + ", " + tname + ", " + tdate);
			}
				
		
			
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
		
	}
	
}
















package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

// View : 사용자가 보게 될 시각적인 요소(화면) 출력 및 입력

public class MemberMenu {
	
	// Scanner 객체 생성 (전역으로 다 쓸 수 있도록)
	private Scanner sc = new Scanner(System.in);
	
	// MemberController 객체 생성 (전역 선언해서 메소드에서 바로 요청할 수 있게끔)
	private MemberController mc = new MemberController();
	
	// Alt Shift J
	/**
	 * 사용자가 보게될 첫 화면(메인 화면)
	 */
	public void mainMenu() {
		
		while(true) {
			
			System.out.println("\n == 회원 관리 프로그램 ==");
			System.out.println("1. 회원 추가");
			System.out.println("2. 회원 전체 조회");
			System.out.println("3. 회원 아이디 검색");
			System.out.println("4. 회원 이름으로 키워드 검색");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("0. 프로그램 종료");
			System.out.println("9. 이름으로 정보 조회");
			
			System.out.print(">> 메뉴 선택 : ");
			int menu = sc.nextInt();
			
			sc.nextLine();
		
			switch(menu) {
			case 1: inputMember(); break;
			case 2: mc.selectList(); break; // 입력받을 거 없으면 바로 controller
			case 3: 
//					String userId = inputMemberId();
//					mc.selectByUserId(userId); 
					mc.selectByUserId(inputMemberId());
					break;
			case 4: 
					String keyWord = inputMemberName();
					mc.selectByUserName(keyWord);
					break;
			case 5: 
					updateMember();
					break;
			case 6: 
//					String deleteId = inputMemberId();
//					mc.deleteMember(deleteId);
					mc.deleteMember(inputMemberId());
					break;
			case 0: System.out.println("이용해주셔서 감사합니다."); return;
			case 9: break;
			default : System.out.println("메뉴를 잘못입력하셨습니다. 다시 입력해주세요.");
			}
		}
		
	}
	
	/**
	 *  회원 추가 창 (서브화면)
	 *  추가하고자 하는 회원의 정보를 입력받아서 회원 추가요청하는 창
	 */
	public void inputMember() {
		System.out.println("\n===== 회원 추가 =====");
		
		// 아이디 ~ 취미 까지 입력받기
		System.out.print("아이디 : ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호 : ");
		String userPwd = sc.nextLine();
		
		System.out.print("이름 : ");
		String userName = sc.nextLine();
		
		System.out.print("성별(M/F) : ");
		String gender = sc.nextLine();
		
		System.out.print("나이 : "); // 이제부터 문자열로 다 받자! => 웹에서는 다 문자로 넘어오기때문에
		String age = sc.nextLine(); // "19"
		
		System.out.print("이메일 : ");
		String email = sc.nextLine();
		
		System.out.print("전화번호(- 빼고 입력) : ");
		String phone = sc.nextLine();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		System.out.print("취미(,로 연이어 작성) : ");
		String hobby = sc.nextLine();
		
		// 회원 추가 요청 ==> Controller 메소드 호출
		mc.insertMember(userId, userPwd, userName, gender, age, email, phone, address, hobby);
		
	}
	
	/**
	 * 사용자에게 회원 아이디 입력 받은 후 입력된 값을 반환 시켜주는 메소드
	 * @return 사용자가 입력한 아이디값
	 */
	public String inputMemberId() {
		System.out.print("\n 회원 아이디 입력 : ");
		return sc.nextLine();
	}
	
	/**
	 * 사용자에게 검색할 회원명(키워드) 입력받은 후 그때 입력된 값을 반환시켜주는 메소드
	 * @return 사용자가 입력한 회원명(키워드)
	 */
	public String inputMemberName() {
		System.out.print("회원 이름(키워드) 입력 : ");
		return sc.nextLine();
	}
	
	public void updateMember() {
		System.out.println("\n === 회원정보 변경 ===");
		
		// 아이디 => 바꿀 비번, 이메일, 전번, 주소
//		System.out.print("회원정보 변경할 회원 아이디 입력 : ");
//		String userID = sc.nextLine();
		
		String userId = inputMemberId();
		
		System.out.print("변경할 비밀번호 : ");
		String userPwd = sc.nextLine();
		
		System.out.print("변경할 이메일 : ");
		String email = sc.nextLine();
		
		System.out.print("변경할 전번 : ");
		String phone = sc.nextLine();
		
		System.out.print("변경할 주소 : ");
		String address = sc.nextLine();
		
		mc.updateMember(userId, userPwd, email, phone, address);
	}
	
	//------------------------ 응답화면 -------------------------------------------
	
	/**
	 * 서비스 요청 처리 후 성공했을 경우 사용자가 보게될 응답화면
	 * @param message 성공메시지
	 */
	public void displaySuccess(String message) {
		System.out.println("\n 서비스 요청 성공 : " + message);
	}
	
	/**
	 * 서비스 요청 처리 후 실패했을 경우 사용자가 보게 될 응답화면
	 * @param message
	 */
	public void displayFail(String message) {
		System.out.println("\n 서비스 요청 실패 : " + message);
	}
	
	/**
	 * 조회 서비스 요청시 조회결과가 없을 경우 사용자가 보게 될 응답화면
	 * @param message
	 */
	public void displayNoDate(String message) {
		System.out.println(message + "\n");
	}
	
	/**
	 * 조회 서비스 요청시 조회결과가 여러행일 경우 사용자가 보게될 응답화면
	 * @param list
	 */
	public void displayMemberList(ArrayList<Member> list) {
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		
		// 단순 for문
//		for(int i=0 ; i<list.size(); i++) {
//			System.out.println(list.get(i));
//		}
		
		// 향상된 for문 (for each문)
		for(Member m:list) { // m => list.get(0), m => list.get(1)
			System.out.println(m);
		}
		
	}
	
	/**
	 * 조회 서비스 요청시 조회결과가 한 행일 경우 사용자가 보게될 응답화면
	 * @param m
	 */
	public void displayMember(Member m) {
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		System.out.println(m);
	}
}











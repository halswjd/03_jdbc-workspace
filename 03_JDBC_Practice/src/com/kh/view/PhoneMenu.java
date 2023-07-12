package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.PhoneController;
import com.kh.model.vo.Phone;

public class PhoneMenu {

	Scanner sc = new Scanner(System.in);
	
	PhoneController pc = new PhoneController();
	
	public void mainMenu() {
		
		while(true) {
			
			System.out.println("\n==============================");
			System.out.println("전화번호부 v1.0");
			System.out.println("--------------------------");
			System.out.println("1. 전화번호 등록");
			System.out.println("2. 전화번호 검색");
			System.out.println("3. 전화번호 모두 보기");
			System.out.println("4. 종료");
			
			System.out.print(">> 메뉴 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			
			case 1 : inputPhone(); break;
			case 2 : break;
			case 3 : pc.selectPhone(); break;
			case 4 :
					System.out.println("프로그램을 종료합니다.");
					return;
			default :
					System.out.println("잘못 입력하셨습니다.");
					break;
			}
		}
		
	}//
	
	public void inputPhone() {
		
		System.out.println("=== 전화번호 등록 ===");
		System.out.print("이름 : ");
		String name = sc.nextLine();
				
		System.out.print("나이 : ");
		String age = sc.nextLine();
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		System.out.print("전화번호( - 포함) : ");
		String phone = sc.nextLine();
		
		pc.inputPhone(name, age, address, phone);
	}
	
	public void displayList(ArrayList<Phone> list) {
		
		for(Phone p : list) {
			System.out.println(p);
		}
	}
	
//	------------------------------- 응답화면 ----------------------------------------
	
	public void displaySuccess(String message) {
		System.out.println("\n 서비스 요청 성공 : " + message);
	}
	
	public void displayFail(String message) {
		System.out.println("\n 서비스 요청 실패 : " + message);
	}
}

package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.ProductController;
import com.kh.model.vo.Product;

public class ProductMenu {
	
	Scanner sc = new Scanner(System.in);
	ProductController pc = new ProductController();

	/**
	 * 메인화면
	 */
	public void mainMenu() {
		
		while(true) {
			
			System.out.println("==========================================");
			System.out.println("1. 전체 조회 하기");
			System.out.println("2. 상품 추가 하기");
			System.out.println("3. 상품 수정 하기");
			System.out.println("4. 상품 삭제 하기");
			System.out.println("5. 상품 검색 하기");
			System.out.println("0. 프로그램 종료하기");
			System.out.println("==========================================");
			
			System.out.println();
			System.out.print(">> 메뉴 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1 : pc.selectList();
					 break;
			case 2 : inputProduct();
					 break;
			case 3 : updateProduct();
					 break;
			case 4 : pc.deleteProduct(inputId());
					 break;
			case 5 : pc.searchProduct(inputId());
					 break;
			case 0 : 
				System.out.println("이용해주셔서 감사합니다. 프로그램을 종료합니다..."); 
				return;
			default :
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
				break;
			}
		}
		
		
	}//
	
	/**
	 * 조회 쿼리문 출력 메소드
	 * @param list
	 */
	public void displayProductList(ArrayList<Product> list) {
		
		for(Product p : list) {
			System.out.println(p);
		}
	}
	
	/**
	 * 상품 추가 입력창
	 */
	public void inputProduct() {
		System.out.println("\n=== 상품 추가 ===");
		System.out.print("상품 아이디 : ");
		String productId = sc.nextLine();
		
		System.out.print("상품명 : ");
		String pName = sc.nextLine();
		
		System.out.print("상품가격 : ");
		String price = sc.nextLine();
		
		System.out.print("상품 상세 정보 : ");
		String description = sc.nextLine();
		
		System.out.print("재고 : ");
		String stock = sc.nextLine();
		
		pc.inputProduct(productId, pName, price, description, stock);
		
	}
	
	/**
	 * 상품 수정 입력창
	 */
	public void updateProduct() {
		
		System.out.println("\n=== 상품 수정 ====");
		String productId = inputId();
		
		System.out.println();
		System.out.println(">> " + productId + " 상품 수정 <<");
		System.out.print("상품명 : ");
		String pName = sc.nextLine();
		
		System.out.print("상품가격 : ");
		String price = sc.nextLine();
		
		System.out.print("상품 상세 정보 : ");
		String description = sc.nextLine();
		
		System.out.print("재고 : ");
		String stock = sc.nextLine();
		
		pc.updateProduct(productId, pName, price, description, stock);
		
	}
	
	public String inputId() {
		
		System.out.print("상품 아이디 : ");
		String productId = sc.nextLine();
		
		return productId;
	}
	
	public void displayProduct(Product p) {
		System.out.println(p);
	}
	
	//-------- 응답화면 -------
	
	/**
	 * 요청사항 실패 화면
	 * @param message
	 */
	public void displayFail(String message) {
		System.out.println("\n 요청 사항 실패 : " + message);
	}
	
	
	/**
	 * 요청사항 성공 화면
	 * @param message
	 */
	public void displaySuccess(String message) {
		System.out.println("\n 요청 사항 성공 : " + message);
	}
}

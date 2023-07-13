package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductMenu;

public class ProductController {
	
	public void selectList() {
		ArrayList<Product> list = new ProductService().selectList();
		
		if(list.isEmpty()) {
			new ProductMenu().displayFail("등록된 상품이 없습니다.");
		} else {
			new ProductMenu().displayProductList(list);
		}
	}
	
	public void inputProduct(String productId, String pName, String price, String description, String stock) {
		Product p = new Product(productId, pName, Integer.parseInt(price), description, Integer.parseInt(stock));
		
		int result = new ProductService().inputProduct(p);
		
		if(result >0) {
			new ProductMenu().displaySuccess("성공적으로 상품이 추가되었습니다.");
		} else {
			new ProductMenu().displayFail("상품 추가에 실패하였습니다.");
		}
				
	}
	
	public void updateProduct(String productId, String pName, String price, String description, String stock) {
		
		Product p = new Product(productId, pName, Integer.parseInt(price), description, Integer.parseInt(stock));
		
		int result = new ProductService().updateProduct(p);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("성공적으로 상품을 수정하였습니다.");
		} else {
			new ProductMenu().displayFail(productId + "에 해당하는 상품아이디를 찾지못했습니다.");
		}
		
	}
	
	public void deleteProduct(String productId) {
		
		int result = new ProductService().deleteProduct(productId);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("성공적으로 상품을 삭제하였습니다.");
		} else {
			new ProductMenu().displayFail(productId + "에 해당하는 상품아이디를 찾지못했습니다.");
		}
	}
	
	public void searchProduct(String productId) {
		
		ArrayList<Product> list = new ProductService().searchProduct(productId);
		
		if(list.isEmpty()) {
			new ProductMenu().displayFail(productId + "에 해당하는 상품을 찾지 못했습니다.");
		} else {
			new ProductMenu().displayProductList(list);
		}
		
	}
}









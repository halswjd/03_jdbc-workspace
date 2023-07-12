package com.kh.controller;

import java.lang.reflect.Member;
import java.util.ArrayList;

import com.kh.model.service.PhoneService;
import com.kh.model.vo.Phone;
import com.kh.view.PhoneMenu;

public class PhoneController {
	
	public void inputPhone(String name, String age, String address, String phone) {
		
		Phone p = new Phone(name, Integer.parseInt(age), address, phone);
		
		int result = new PhoneService().inputPhone(p);
		
		String message = "";
		
		if(result > 0) {
			message = "전화번호 등록 성공!!";
		} else {
			message = "전화번호 등록 실패ㅠㅠ";
		}
		
		new PhoneMenu().displaySuccess(message);
	}
	
	public void selectPhone() {
		
		ArrayList<Phone> list = new PhoneService().selectPhone();
		
		if(list.isEmpty()) {
			new PhoneMenu().displayFail("등록된 번호가 없습니다.");
		} else {
			new PhoneMenu().displayList(list);
		}
		
	}

}

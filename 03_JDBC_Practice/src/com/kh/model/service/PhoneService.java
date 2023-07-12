package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kh.common.PhoneTemplate.*;

import com.kh.common.PhoneTemplate;
import com.kh.model.dao.PhoneDao;
import com.kh.model.vo.Phone;

public class PhoneService {
	
	
	
	public int inputPhone(Phone p) {
		
		Connection conn = getConnection();
		int result = new PhoneDao().insertPhone(conn,p);
		
		// 트랜젝션 처리
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
	}
	
	public ArrayList<Phone> selectPhone() {
		
		Connection conn = getConnection();
		
		ArrayList<Phone> list = new PhoneDao().selectPhone(conn);
		
		PhoneTemplate.close(conn);
		
		return list;
		
	}
}

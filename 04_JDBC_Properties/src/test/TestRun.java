package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class TestRun {

	public static void main(String[] args) {
		
		// Properties 복습
		/*
		 *  * Properties 특징
		 *  - Map 계열 컬렉션 (key + value 세트로 담는 특징)
		 *  - key, value 모두 String(문자열)으로 담기
		 *    값 세팅 - setProperty(String key, String value)
		 *    값 출력 - getProperty(String key) : String value
		 *    
		 *  - 주로 외부 파일(.properties / .xml)로 입출력할 때 사용 => 환경설정 파일 같은거... 개발자가 아닌 사람이 보는거
		 */
		
		/*
		// CRUD : Create, Read(Select), Update, Delete
		Properties prop = new Properties();
		prop.setProperty("C", "INSERT");
		prop.setProperty("R", "SELECT");
		prop.setProperty("U", "UPDATE");
		prop.setProperty("D", "DELETE");
		
		// 순서 유지하지 않고 막 담김
		
		try {
			prop.store(new FileOutputStream("resources/test.properties"), "properties Test");
			prop.storeToXML(new FileOutputStream("resources/test.xml"), "properties Test");
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		Properties prop = new Properties(); // 텅비어있는 상태
		
		try {
			prop.load(new FileInputStream("resources/driver.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(prop.getProperty("driver"));
		System.out.println(prop.getProperty("url"));
		System.out.println(prop.getProperty("username"));
		System.out.println(prop.getProperty("password"));
		System.out.println(prop.getProperty("candy")); // 없는 key값 제시 => null 
		
		
	}
	
}
















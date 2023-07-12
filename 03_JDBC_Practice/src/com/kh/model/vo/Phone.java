package com.kh.model.vo;

public class Phone {

	private int no;
	private String name;
	private int age;
	private String address;
	private String phone;
	
	public Phone() {
		
	}
	
	public Phone(int no, String name, int age, String address, String phone) {
		super();
		this.no = no;
		this.name = name;
		this.age = age;
		this.address = address;
		this.phone = phone;
	}

	public Phone(String name, int age, String address, String phone) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
		this.phone = phone;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Phone [no=" + no + ", name=" + name + ", age=" + age + ", address=" + address + ", phone=" + phone
				+ "]";
	}
	
	
	
}

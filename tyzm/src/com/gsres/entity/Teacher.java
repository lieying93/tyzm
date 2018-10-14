package com.gsres.entity;

import java.io.Serializable;

public class Teacher  implements Serializable{
     
	 private static final long serialVersionUID = 1L; //°æ±¾ºÅ
	 private String name ="";
	 private String inputName ="";
	 private String passwd = "";
	 private String loginName = "";
	 private String password = "";
	 
	 public Teacher(String name, String inputName, String passwd, String loginName, String password) {
		super();
		this.name = name;
		this.inputName = inputName;
		this.passwd = passwd;
		this.loginName = loginName;
		this.password = password;
	 }
	 
	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Teacher [name=" + name + ", inputName=" + inputName + ", passwd=" + passwd + ", loginName=" + loginName
				+ ", password=" + password + "]";
	}
	 
}

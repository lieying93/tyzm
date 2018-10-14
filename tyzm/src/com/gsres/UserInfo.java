package com.gsres;

import net.sf.json.JSONObject;

public class UserInfo {
    private String inputType="";
    private String oldLoginName="";
    private String loginName ="";
    private String isOldUser ="";
    private String reviewStatus ="";
    private String userType ="";
    private String userId="";
    private String isZjUser="";
    private String password="";
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getOldLoginName() {
		return oldLoginName;
	}
	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getIsOldUser() {
		return isOldUser;
	}
	public void setIsOldUser(String isOldUser) {
		this.isOldUser = isOldUser;
	}
	public String getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsZjUser() {
		return isZjUser;
	}
	public void setIsZjUser(String isZjUser) {
		this.isZjUser = isZjUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public UserInfo(JSONObject jsonObj) {
		 
	}
	@Override
	public String toString() {
		return "UserInfo [inputType=" + inputType + ", oldLoginName=" + oldLoginName + ", loginName=" + loginName
				+ ", isOldUser=" + isOldUser + ", reviewStatus=" + reviewStatus + ", userType=" + userType + ", userId="
				+ userId + ", isZjUser=" + isZjUser + ", password=" + password + "]";
	}
    
}

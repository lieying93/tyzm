package com.gsres;

import org.junit.Test;

import net.sf.json.JSONObject;

public class UserInfoTest {
	@Test
    public void parseGetLoginName(){
    	Client client = new Client();
    	String result = "{\"inputType\":\"id_card_no\",\"oldLoginName\":\"\\u738b\\u6dd1\\u7434\",\"loginName\":\"gsres_iflytek_d9dd7d07a7ca9c9a5c7c33f510afddc8\",\"isOldUser\":1,\"reviewStatus\":\"110002\",\"userType\":\"002\",\"userId\":\"3162000039000195022\"}";
    	JSONObject userInfo = client.parseGetLoginName(result);
    	System.out.println("userInfo.isOldUser="+userInfo.getString("isOldUser"));
    	System.out.println("userInfo.loginName="+userInfo.getString("loginName"));
    	System.out.println("userInfo.oldLoginName="+userInfo.getString("oldLoginName"));
    }
}

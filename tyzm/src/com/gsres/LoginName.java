package com.gsres;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gsres.entity.Teacher;
import com.gsres.util.DomTeach;

import net.sf.json.JSONObject;

public class LoginName {
    private Teacher teacher = null;
    CloseableHttpClient client = HttpClients.createDefault();//ʵ����httpclient
    HttpResponse response = null;
    
	public LoginName(Teacher teacher) {
		super();
		this.teacher = teacher;
	}
	String rawHtml;
	private CloseableHttpResponse execute=null;
	private HttpEntity entity = null;
    
	public String login() {
		String result =""; 
        HttpGet getLoginPage = new HttpGet("http://tyzm.gsres.cn/index.php?app=Home&mod=Index&act=login");//���񴦵�½ҳ��get
        try {
            //�򿪽���
            execute = client.execute(getLoginPage);
            //��ȡ��֤��
            getVerifyingCode();
            //�����û���������֤��
            System.out.println("verifying code has been save as verifyCode.jpeg, input its content");
            String code=null;
            Scanner in = new Scanner(System.in);
            code = in.nextLine();
            in.close();
            //�趨post����������ͼ�е�����һ��
            HttpPost post = new HttpPost("http://tyzm.gsres.cn/index.php?app=Home&mod=Ajax&act=getLoginName");//����post����
            ArrayList<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
            postData.add(new BasicNameValuePair("inputName", teacher.getInputName()));
            postData.add(new BasicNameValuePair("verifyCode", code));
            postData.add(new BasicNameValuePair("unreset", "1"));//��֤��
            post.setEntity(new UrlEncodedFormEntity(postData));//�������
            response = client.execute(post);//ִ�е�½��Ϊ
            rawHtml = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(rawHtml);
            result = rawHtml;
        } catch (ClientProtocolException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
  private  void getVerifyingCode() {
        HttpGet getVerifyCode = new HttpGet("http://tyzm.gsres.cn/index.php?app=Home&mod=Ajax&act=getVerifyCode");//��֤��get
        FileOutputStream fileOutputStream = null;
        HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//��ȡ��֤��
            /*��֤��д���ļ�,��ǰ���̵ĸ�Ŀ¼,����ΪverifyCode.jped*/
            fileOutputStream = new FileOutputStream(new File("D:/verifycode.jpg"));
            response.getEntity().writeTo(fileOutputStream);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public JSONObject parseGetLoginName(String result){
    	
    	JSONObject jsonObj = JSONObject.fromObject(result);
    	JSONObject userInfo = null;
    	String status = jsonObj.getString("status");
    	if("200".equals(status)){
    	    userInfo = jsonObj.getJSONObject("data"); 
    		userInfo.put("isZjUser", 0);
    		userInfo.put("password", teacher.getPasswd());
    	}
  	    return userInfo;
    }
    public static void main(String[] args) throws Exception {
    	List<Teacher> teachers = DomTeach.getTeachers();
		for (Teacher teacher : teachers) {
			if("622822198503054568".equals(teacher.getInputName())){
				LoginName client = new LoginName(teacher);
					String getLoginNameResult = client.login();
					JSONObject userInfo = client.parseGetLoginName(getLoginNameResult);		
			}
		}
		
	}
}


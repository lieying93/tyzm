package com.amarsoft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

import com.gsres.Login;

import net.sf.json.JSONObject;

public class Client {
    private String accout="xlma";
    private String password="";
    private String code = "";
    CloseableHttpClient client = HttpClients.createDefault();//实例化httpclient
    HttpResponse response = null;
    String rawHtml;
	private CloseableHttpResponse execute=null;
	private HttpEntity entity=null;
    
    public Client(String accout, String password) {
        super();
        this.accout = accout;
        this.password = password;
    }
 
    public Client() {
		// TODO Auto-generated constructor stub
	}

	public String login() {
		String result =""; 
        HttpGet getLoginPage = new HttpGet("https://home.amarsoft.com/sso/ssologin.jsp");//教务处登陆页面get
        
        try {
            //打开教务处
            execute = client.execute(getLoginPage);
            getVerifyingCode();
            //获取验证码
//            boolean flag = true;
//            while(flag){
//            	getVerifyingCode();
//            	code = Login.getImage("D:/verifycode.jpg");
//            	 System.out.println("code="+code );
//            	if(code.length()==5){
//            		flag = false;
//            	}
//            }
            
            //提醒用户并输入验证码
            System.out.println("verifying code has been save as verifyCode.jpeg, input its content");
           
            String code=null;
            Scanner in = new Scanner(System.in);
            code = in.nextLine();
            in.close();
            //设定post参数，和上图中的内容一致
            ArrayList<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
            postData.add(new BasicNameValuePair("account", accout));
            postData.add(new BasicNameValuePair("captcha", code));
            postData.add(new BasicNameValuePair("password", password));//验证码
            
            HttpPost post = new HttpPost("https://home.amarsoft.com/sso/ssoservice");//构建post对象
            post.setEntity(new UrlEncodedFormEntity(postData));//捆绑参数
            response = client.execute(post);//执行登陆行为
            
        } catch (ClientProtocolException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    
  private  void getVerifyingCode() {
        HttpGet getVerifyCode = new HttpGet("https://home.amarsoft.com/sso/Kaptcha.jpg");//验证码get
        FileOutputStream fileOutputStream = null;
        HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//获取验证码
            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/
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
    		userInfo.put("password", this.password);
    	}
  	    return userInfo;
    }
    public String getFirstPage(){
    	 HttpGet getFirstPage = new HttpGet("https://home.amarsoft.com/sso/home.jsp");//登录home的jsp
    	 try {
			execute = client.execute(getFirstPage);
			String firstContent = EntityUtils.toString(execute.getEntity(), "utf-8");
			//System.out.println("firstContent="+firstContent);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		return "";
    }
    public String getEIPPage(){
    	HttpGet getFirstPage = new HttpGet("https://home.amarsoft.com/EIP/desktop/mydesktop.view");//登录EIP连接
    	try {
    		execute = client.execute(getFirstPage);
    		String firstContent = EntityUtils.toString(execute.getEntity(), "utf-8");
    		//System.out.println("firstContent="+firstContent);
    	} catch (ClientProtocolException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return "";
    }
    public String getRegisterPage(){
        try {
			String url = "https://home.amarsoft.com/EIP/checkon/oneday/register.view";
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("workdate", "2018/09/01"));
			String  str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
			HttpGet httpGet = new HttpGet(url + "?" + str);
			execute = client.execute(httpGet );
	    	String  content =EntityUtils.toString(execute.getEntity(), "utf-8");
	    	System.out.println("content="+content);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "";
    }
    
    public static void main(String[] args) {
		Client client = new Client();
	    client.login();
	    client.getFirstPage();
	    //得到EIP页面元素
	    client.getEIPPage();
	    //得到填写考勤页面
	    client.getRegisterPage();
	}
}


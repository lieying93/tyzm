package com.gsres;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.Consts;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Login {
	static HttpClient client = new HttpClient();
    private static String username="622822196703044525";
    private static String password= "wsq0304";
    private static String URL="http://tyzm.gsres.cn/index.php?app=Home&mod=Index&act=login";
	private static HostConfiguration hostConfiguration;
	public static void main(String[] args) throws Exception {
		String fileName ="D:\\verifycode.png";
		URL url = new URL("http://tyzm.gsres.cn/index.php?app=Home&mod=Index&act=login");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET"); // 以Post方式提交表单，默认get方式
		String cookie = con.getHeaderField("set-cookie");
		System.out.println("cookie="+cookie);
		ImageIO.write(ImageIO.read(con.getInputStream()), "PNG", new File(fileName));
		String code = getImage(fileName);
		System.out.println("验证码为：" + code);
		String ver="http://tyzm.gsres.cn/index.php?app=Home&mod=Ajax&act=getLoginName";
		postForm2(ver,username,code,cookie);
		//postRequest(username,password,code);
	}
	//模拟登录方式2
			public static void postForm2(String uri,String username,String code,String cookie){
				try {
					CloseableHttpClient httpclient = HttpClients.createDefault();
					HttpClientContext context = HttpClientContext.create();
					HttpPost httpPost = new HttpPost(uri);
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			        nvps.add(new NameValuePair("username", username));
			        nvps.add(new NameValuePair("verifyCode", code));
			        nvps.add(new NameValuePair("unreset", "1"));
			        httpPost.setHeader("Cookie", cookie);
			        httpPost.setHeader("Host", "tyzm.gsres.cn");
			        httpPost.setHeader("Referer", "http://tyzm.gsres.cn/index.php?app=Home&mod=Ajax&act=getLoginName");
			        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; W…) Gecko/20100101 Firefox/61.0");
			        CloseableHttpResponse response = httpclient.execute(httpPost, context);
			        String result="";
			        StringBuffer buffer = new StringBuffer();
					BufferedReader reader = null;
					// 定义BufferedReader输入流来读取URL的响应
					reader = new BufferedReader(new InputStreamReader(
							 response.getEntity().getContent(), "utf-8"));
					String line = null;
					while ((line = reader.readLine()) != null) {
						buffer.append(line);
					}
					result = buffer.toString();
					System.out.println(result);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	//模拟登录
	 private static void postRequest(String username, String password,Map<String,String> code) {
	        PostMethod postMethod = new PostMethod("http://tyzm.gsres.cn/index.php?app=Home&mod=Ajax&act=getLoginName");
	        NameValuePair[] data = {
	        		new NameValuePair("inputName", username),
	                new NameValuePair("verifyCode", code.get("code")),
	                new NameValuePair("unreset", "1") };
	        postMethod.setRequestBody(data);
	        try {
	            client.executeMethod(postMethod);
	            String text = postMethod.getResponseBodyAsString();
	            System.out.println("text="+text);
	            // 匹配结果集中是否有匹配的字符串
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	//获取验证码值
	public static String getImage(String  fileName){
		File storeFile =new File(fileName);
		String valCode = null;
		try {
			valCode = new OCR().recognizeText(storeFile, "jpeg");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valCode;
	}
}

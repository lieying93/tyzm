package com.gsres;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.synth.SynthSeparatorUI;

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

public class Client {
    private Teacher teacher = null;
    CloseableHttpClient client = HttpClients.createDefault();//实例化httpclient
    HttpResponse response = null;
    private static int readHistroyYema=1;
    private static List<String> list = new ArrayList<String>();
	public Client(Teacher teacher) {
		super();
		this.teacher = teacher;
	}
	String rawHtml;
	private CloseableHttpResponse execute=null;
	private HttpEntity entity = null;
    
	public String login() {
		String result =""; 
        HttpGet getLoginPage = new HttpGet("http://tyzm.gsres.cn/index.php?app=Home&mod=Index&act=login");//教务处登陆页面get
        try {
            //打开教务处
            execute = client.execute(getLoginPage);
            //获取验证码
            getVerifyingCode();
            //提醒用户并输入验证码
            System.out.println("verifying code has been save as verifyCode.jpeg, input its content");
            String code=null;
            Scanner in = new Scanner(System.in);
            code = in.nextLine();
            in.close();
            //设定post参数，和上图中的内容一致
            HttpPost post = new HttpPost("http://tyzm.gsres.cn/index.php?app=Home&mod=Ajax&act=getLoginName");//构建post对象
            ArrayList<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
            postData.add(new BasicNameValuePair("inputName", teacher.getInputName()));
            postData.add(new BasicNameValuePair("verifyCode", code));
            postData.add(new BasicNameValuePair("unreset", "1"));//验证码
            post.setEntity(new UrlEncodedFormEntity(postData));//捆绑参数
            response = client.execute(post);//执行登陆行为
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
        HttpGet getVerifyCode = new HttpGet("http://tyzm.gsres.cn/index.php?app=Home&mod=Ajax&act=getVerifyCode");//验证码get
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
    		userInfo.put("password", teacher.getPasswd());
    	}
  	    return userInfo;
    }
    public JSONObject getLoginTicket() throws Exception{
    	String ssoRestUrl = "http://sso.edu.gsres.cn/sso//login?service=http%3A%2F%2Fi.changyan.com%2Ficloud%3Ffrom%3Dew";
    	HttpGet sLogin = new HttpGet(ssoRestUrl);
    	execute = client.execute(sLogin);
    	String  loginContent =EntityUtils.toString(execute.getEntity(), "utf-8"); 
    	loginContent = loginContent.replace("/\t/g", "")
    			                   .replace("\\", "")
    			                   .replace("(", "")
    			                   .replace(")", "")
    			                   .replace("'", "");
  //  	System.out.println(loginContent);
    	JSONObject jsonObj = JSONObject.fromObject(loginContent);
    	JSONObject jsonData = jsonObj.getJSONObject("data");
    	System.out.println("jsonData-lt="+jsonData.getString("lt"));
    	System.out.println("jsonData-execution="+jsonData.getString("execution"));
    	return jsonData;
    }
    public String getSSOLogin(JSONObject userInfo,JSONObject ticketData) throws Exception{
    	String url = "http://sso.edu.gsres.cn/sso//login";
    	
    	ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
    	params.add(new BasicNameValuePair("_eventId", "submit"));
    	params.add(new BasicNameValuePair("appId", "KtSNKxk3"));
    	params.add(new BasicNameValuePair("callback", "jQuery17209531084851449882_1535173119476"));
    	params.add(new BasicNameValuePair("encode", "true"));
    	params.add(new BasicNameValuePair("execution", ticketData.getString("execution")));
    	params.add(new BasicNameValuePair("key", "login_name"));
    	params.add(new BasicNameValuePair("lt", ticketData.getString("lt")));
    	params.add(new BasicNameValuePair("password", teacher.getPassword()));
    	params.add(new BasicNameValuePair("service", "http://i.changyan.com/icloud?from=ew"));
    	params.add(new BasicNameValuePair("username",teacher.getLoginName() ));
    	String  str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
    	HttpGet sLogin = new HttpGet(url + "?" + str);
    	execute = client.execute(sLogin );
    	String  content =EntityUtils.toString(execute.getEntity(), "utf-8");
    	System.out.println("content="+content);
    	return "";
    }
    public String index() throws Exception{
    	String url = "http://www.gsres.cn/cloud/index.php?from=ew";
    	HttpGet sLogin = new HttpGet(url);
    	execute = client.execute(sLogin );
    	String  content =EntityUtils.toString(execute.getEntity(), "utf-8");
    	//System.out.println("index-->content="+content);
    	return "";
    }
    public String gsjsxy () throws Exception{
    	String url = "http://tyzm.gsres.cn/index.php?app=Home&mod=App&act=openApp&appId=4000000000000000013";
    	HttpGet sLogin = new HttpGet(url);
    	execute = client.execute(sLogin );
    	String  content =EntityUtils.toString(execute.getEntity(), "utf-8");
    	System.out.println("gsjsxy-->content="+content);
    	return "";
    }
    public String[][] readHistory(int yema) throws Exception{
    	String url = "http://rwsy.gsres.cn/wx/readhistory.htm";
    	if(1!=yema){
    		url=url+"?id=&page="+yema;
    	}
    	HttpGet sLogin = new HttpGet(url);
    	execute = client.execute(sLogin );
    	String  content =EntityUtils.toString(execute.getEntity(), "utf-8");
    	System.out.println("readhistory-->content="+content);
    	if(1==yema){
    		//解析出一共有多少页
       	 Pattern pattern=Pattern.compile("每页20条记录，当前第.*页");  //匹配ER_所在的所有行
       	 Matcher math=pattern.matcher(content);
       	 while (math.find()) {
       		 try {
       			Client.readHistroyYema = Integer.parseInt(math.group(0).replace("每页20条记录，当前第", "").replace("页", "").replace("1/", ""));
   			} catch (Exception e) {
   				System.out.println("解析页码出错");
   			}
       	 }
    	}
    	Document doc = Jsoup.parse(content);
    	Elements liArray = doc.select("div.Main").select("ul").select("li");
    	String[][] read = new String [liArray.size()][3];
    	for(int i =0; i< liArray.size();i++){
    		System.out.println("第"+(i+1)+"条已读记录:");
    		System.out.println("标题:"+liArray.get(i).select("div.text_qishu").html());
    		System.out.println("链接:"+liArray.get(i).select("a").attr("href"));
    		read[i][0]=liArray.get(i).select("a").attr("href");
    		read[i][1]=liArray.get(i).select("div.text_qishu").text();
    		read[i][2]=liArray.get(i).select("div.datetime").text();    		
    	}
    	return read;
    	
    }
    public String readDetails(String href,String title,String date ) throws Exception{
    	String unReadHref = "http://rwsy.gsres.cn/wx/";
    	//System.out.println("已读页面的地址="+unReadHref+href);
    	HttpGet readUrl = new HttpGet(unReadHref+href);
    	execute = client.execute(readUrl );
    	String  content =EntityUtils.toString(execute.getEntity(), "utf-8");
    	//System.out.println("readHistory-->content="+content);
    	//正则表达式
    	Pattern pattern=Pattern.compile("var chapterId = .*;");  //匹配ER_所在的所有行
   	    Matcher math=pattern.matcher(content);
   	    String huifuma="";
   	    while (math.find()) {
   	    	huifuma = math.group(0).replace("var chapterId = '", "").replace("';", "");
   	    }
   	    //System.out.print("标题--"+title + "  回复码--"+huifuma);
   	    Client.list.add("标题--"+title + "  回复码--"+huifuma+ "  时间--"+date);
    	return "";
    	
    }
    
    public String[][] unRead() throws Exception{
    	//每页20条记录,每次运行只读取首页的未读记录数,第一列是href地址,第二列是标题
    	
    	String url = "http://rwsy.gsres.cn/wx/unreadhistory.htm";
    	HttpGet sLogin = new HttpGet(url);
    	execute = client.execute(sLogin );
    	String  content =EntityUtils.toString(execute.getEntity(), "utf-8");
    	System.out.println("unreadhistory-->content="+content);
    	Document doc = Jsoup.parse(content);
    	Elements liArray = doc.select("div.Main").select("ul").select("li");
    	String[][] unRead = new String [liArray.size()][3];
    	for(int i =0; i< liArray.size();i++){
    		System.out.println("第"+(i+1)+"条未读记录:");
    		System.out.println("标题:"+liArray.get(i).select("div.text_qishu").html());
    		System.out.println("链接:"+liArray.get(i).select("a").attr("href"));
    		unRead[i][0]=liArray.get(i).select("a").attr("href");
    		unRead[i][1]=liArray.get(i).select("div.text_qishu").text();
    		unRead[i][2]=liArray.get(i).select("div.datetime").text();
    	}
    	return unRead;
    }
    public String read(String href,String title) throws Exception{
    	String unReadHref = "http://rwsy.gsres.cn/wx/read.htm";
    	System.out.println(unReadHref+href.replace("content.htm", ""));
    	HttpGet readUrl = new HttpGet(unReadHref+href.replace("content.htm", ""));
    	execute = client.execute(readUrl );
    	String  content =EntityUtils.toString(execute.getEntity(), "utf-8");
    	System.out.println("doingRead-->content="+content);
    	
    	return "";
    }
    public static void main(String[] args) throws Exception {
    	List<Teacher> teachers = DomTeach.getTeachers();
		for (Teacher teacher : teachers) {
			if("622822196703044525".equals(teacher.getInputName())){
				Client client = new Client(teacher);
				//	String getLoginNameResult = client.login();
					String getLoginNameResult="{\"status\":200,\"data\":{\"inputType\":\"id_card_no\",\"oldLoginName\":\"\\u738b\\u6dd1\\u7434\",\"isOldUser\":1,\"reviewStatus\":\"110002\",\"userType\":\"002\"}}";
					JSONObject userInfo = client.parseGetLoginName(getLoginNameResult);		
					//经过测试  登录是这个方法
					JSONObject ticketData = client.getLoginTicket();
					client.getSSOLogin(userInfo,ticketData);
					//登录成功 
					client.index();
					//甘肃教学师范主页
					client.gsjsxy(); 
					//获取未读的内容
					String [][]  unRead = client.unRead();
					if(unRead.length>0&&unRead.length<=20){
						//每天读取的文章数设置
						int readNum = unRead.length;
						for(int i =0;i<readNum;i++){
							client.read(unRead[i][0],unRead[i][1]);
							Thread.sleep(2*60*1000);
						}
						
					}
					for(int k=1;k<=Client.readHistroyYema ;k++ ){
						//读出内容
						String [][]  read = client.readHistory(k);
						int readHistoryNum = read.length;
						for(int i =0;i<readHistoryNum;i++){
							client.readDetails(read[i][0],read[i][1],read[i][2]);
						}
					}
				    for(String str: Client.list){
				    	System.out.println(str);
				    }  
					
			}
		}
		
	}
}


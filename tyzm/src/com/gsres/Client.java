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
    CloseableHttpClient client = HttpClients.createDefault();//ʵ����httpclient
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
    		//������һ���ж���ҳ
       	 Pattern pattern=Pattern.compile("ÿҳ20����¼����ǰ��.*ҳ");  //ƥ��ER_���ڵ�������
       	 Matcher math=pattern.matcher(content);
       	 while (math.find()) {
       		 try {
       			Client.readHistroyYema = Integer.parseInt(math.group(0).replace("ÿҳ20����¼����ǰ��", "").replace("ҳ", "").replace("1/", ""));
   			} catch (Exception e) {
   				System.out.println("����ҳ�����");
   			}
       	 }
    	}
    	Document doc = Jsoup.parse(content);
    	Elements liArray = doc.select("div.Main").select("ul").select("li");
    	String[][] read = new String [liArray.size()][3];
    	for(int i =0; i< liArray.size();i++){
    		System.out.println("��"+(i+1)+"���Ѷ���¼:");
    		System.out.println("����:"+liArray.get(i).select("div.text_qishu").html());
    		System.out.println("����:"+liArray.get(i).select("a").attr("href"));
    		read[i][0]=liArray.get(i).select("a").attr("href");
    		read[i][1]=liArray.get(i).select("div.text_qishu").text();
    		read[i][2]=liArray.get(i).select("div.datetime").text();    		
    	}
    	return read;
    	
    }
    public String readDetails(String href,String title,String date ) throws Exception{
    	String unReadHref = "http://rwsy.gsres.cn/wx/";
    	//System.out.println("�Ѷ�ҳ��ĵ�ַ="+unReadHref+href);
    	HttpGet readUrl = new HttpGet(unReadHref+href);
    	execute = client.execute(readUrl );
    	String  content =EntityUtils.toString(execute.getEntity(), "utf-8");
    	//System.out.println("readHistory-->content="+content);
    	//������ʽ
    	Pattern pattern=Pattern.compile("var chapterId = .*;");  //ƥ��ER_���ڵ�������
   	    Matcher math=pattern.matcher(content);
   	    String huifuma="";
   	    while (math.find()) {
   	    	huifuma = math.group(0).replace("var chapterId = '", "").replace("';", "");
   	    }
   	    //System.out.print("����--"+title + "  �ظ���--"+huifuma);
   	    Client.list.add("����--"+title + "  �ظ���--"+huifuma+ "  ʱ��--"+date);
    	return "";
    	
    }
    
    public String[][] unRead() throws Exception{
    	//ÿҳ20����¼,ÿ������ֻ��ȡ��ҳ��δ����¼��,��һ����href��ַ,�ڶ����Ǳ���
    	
    	String url = "http://rwsy.gsres.cn/wx/unreadhistory.htm";
    	HttpGet sLogin = new HttpGet(url);
    	execute = client.execute(sLogin );
    	String  content =EntityUtils.toString(execute.getEntity(), "utf-8");
    	System.out.println("unreadhistory-->content="+content);
    	Document doc = Jsoup.parse(content);
    	Elements liArray = doc.select("div.Main").select("ul").select("li");
    	String[][] unRead = new String [liArray.size()][3];
    	for(int i =0; i< liArray.size();i++){
    		System.out.println("��"+(i+1)+"��δ����¼:");
    		System.out.println("����:"+liArray.get(i).select("div.text_qishu").html());
    		System.out.println("����:"+liArray.get(i).select("a").attr("href"));
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
					//��������  ��¼���������
					JSONObject ticketData = client.getLoginTicket();
					client.getSSOLogin(userInfo,ticketData);
					//��¼�ɹ� 
					client.index();
					//�����ѧʦ����ҳ
					client.gsjsxy(); 
					//��ȡδ��������
					String [][]  unRead = client.unRead();
					if(unRead.length>0&&unRead.length<=20){
						//ÿ���ȡ������������
						int readNum = unRead.length;
						for(int i =0;i<readNum;i++){
							client.read(unRead[i][0],unRead[i][1]);
							Thread.sleep(2*60*1000);
						}
						
					}
					for(int k=1;k<=Client.readHistroyYema ;k++ ){
						//��������
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


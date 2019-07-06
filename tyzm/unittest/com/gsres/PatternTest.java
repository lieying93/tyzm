package com.gsres;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class PatternTest {
     @Test
     public void pTest(){
    	 Pattern pattern1=Pattern.compile("每页20条记录，当前第.*页");  //匹配ER_所在的所有行
    	 String str="每页20条记录，当前第1/79页";
    	 Matcher math=pattern1.matcher(str);
    	 	
    	 while (math.find()) {
    	 	System.out.println(math.group(0).replace("每页20条记录，当前第", "").replace("页","").replace("1/",""));
    	 }

     }
     @Test
     public void neirongTest(){
    	 String str="标题--2014年度全国模范教师 — 赵耀直\r\n" + 
    	 		"<div class=\"wdt\">\r\n" + 
    	 		" （未读）\r\n" + 
    	 		"</div>  回复码--92";
    	 str= str.replace("<div class=\"wdt\">\r\n" + 
    	 		" （未读）\r\n" + 
    	 		"</div>", "").replace("\r\n", "");
    	 
    	 
    	 System.out.println(str);
     }
     @Test
     public void buTest(){
    	 String str="标题--陇原名师—榆中县第一中学杨正仁（总第394期—2018.12.7）\r\n" + 
    	 		"<div class=\"ydt\">\r\n" + 
    	 		" （已读）\r\n" + 
    	 		"</div>  回复码--4355";
     	 str= str.replace("<div class=\"ydt\">\r\n" + 
     	 		" （已读）\r\n" + 
     	 		"</div>", "").replace("\r\n", "");
     	 
     	 
     	 System.out.println(str);
     }
     @Test
     public void huifuTest(){
    	 String str="http://rwsy.gsres.cn:81/html/2019/07/190705223904385141134//content.html?rn=1994094393";
    	 System.out.println(str.substring(0,str.indexOf("content.html?rn") )+"code.jpg"); 
     }
     
     @Test
     public void idTest(){
    	 String content="\r\n" + 
    	 		"\r\n" + 
    	 		"\r\n" + 
    	 		"\r\n" + 
    	 		"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n" + 
    	 		"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" + 
    	 		"<head>\r\n" + 
    	 		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n" + 
    	 		"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" /> \r\n" + 
    	 		"\r\n" + 
    	 		"<title>甘肃教师学苑-【教育资讯】全省教育脱贫攻坚现场调度会召开</title>\r\n" + 
    	 		"<link href=\"http://rwsy.gsres.cn:81/front/style/style.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n" + 
    	 		"<script type=\"text/javascript\" src=\"http://rwsy.gsres.cn:81/skins/metal_blue/js/jquery-1.7.1.min.js\"></script>\r\n" + 
    	 		"<script type='text/javascript' src='http://rwsy.gsres.cn:81/xd/util.js'></script>\r\n" + 
    	 		"<script type='text/javascript' src='http://rwsy.gsres.cn:81/xd/xd.js'></script>\r\n" + 
    	 		"<script>\r\n" + 
    	 		"\r\n" + 
    	 		"$(function(){\r\n" + 
    	 		"	/*$(\"#main\").load(function(){\r\n" + 
    	 		"		var mainheight = $(this).contents().find(\"body\").height()+30;\r\n" + 
    	 		"		$(this).height(mainheight);\r\n" + 
    	 		"\r\n" + 
    	 		"	});*/\r\n" + 
    	 		"	var callback = function(data) {\r\n" + 
    	 		"            $('#main').height(data.height);\r\n" + 
    	 		"        };\r\n" + 
    	 		"         \r\n" + 
    	 		"        XD.receiveMessage(callback);\r\n" + 
    	 		"	var msg = '';\r\n" + 
    	 		"	if(msg != ''){\r\n" + 
    	 		"		alert(msg);\r\n" + 
    	 		"	}\r\n" + 
    	 		"	var rec = $(\"#a_title\");\r\n" + 
    	 		"	if(rec[0].innerText == ' >> 【人文素养】去的背影——冯友兰?1946 '){\r\n" + 
    	 		"			rec.innerText = ' >> 【人文素养】去的背影——冯友兰•1946 ';\r\n" + 
    	 		"	}\r\n" + 
    	 		"	\r\n" + 
    	 		"});\r\n" + 
    	 		"var canread = true;\r\n" + 
    	 		"var isRead = false;\r\n" + 
    	 		"function read(){\r\n" + 
    	 		"	if(trim($(\"#courseNum1\").val()) == ''){\r\n" + 
    	 		"		alert('内容编号输入错误');\r\n" + 
    	 		"		$(\"#courseNum1\").focus();\r\n" + 
    	 		"		return;\r\n" + 
    	 		"	}\r\n" + 
    	 		"	if(!canread){\r\n" + 
    	 		"		alert('根据省教育厅“关于实施甘肃省教师人文素养提升工程的通知”，“甘肃教师学苑”从2014年11月1日起记录阅读学分，11月1日之前发布的文章可以正常阅读，但不记录学分，请各位老师周知。');\r\n" + 
    	 		"	}\r\n" + 
    	 		"	if(!isRead){\r\n" + 
    	 		"		isRead = true;\r\n" + 
    	 		"		$(\"#courseNum\").val($(\"#courseNum1\").val());\r\n" + 
    	 		"		$(\"#myForm\").submit();\r\n" + 
    	 		"	}\r\n" + 
    	 		"}\r\n" + 
    	 		"function trim(str) {   \r\n" + 
    	 		"    return str.replace(/(^\\s*)|(\\s*$)/g, \"\");   \r\n" + 
    	 		"}\r\n" + 
    	 		"</script>\r\n" + 
    	 		"</head>\r\n" + 
    	 		"\r\n" + 
    	 		"<body>\r\n" + 
    	 		"\r\n" + 
    	 		"<center>\r\n" + 
    	 		"<form action=\"read.htm\" id=\"myForm\" method=\"post\">\r\n" + 
    	 		"<input type=\"hidden\" name=\"page\" value=\"1\"/>\r\n" + 
    	 		"<input type=\"hidden\" name=\"id\" value=\"190705222929196141129\"/>\r\n" + 
    	 		"<input type=\"hidden\" name=\"sid\" value=\"\"/>\r\n" + 
    	 		"<input type=\"hidden\"  id=\"courseNum\" name=\"courseNum\"/>\r\n" + 
    	 		" </form>\r\n" + 
    	 		"   <div class=\"TOP\">\r\n" + 
    	 		"          <div class=\"TOP_mian\">\r\n" + 
    	 		"             <div class=\"TOP_title\"><a href=\"index.htm\"><img src=\"http://rwsy.gsres.cn:81/img/title_blue.jpg\" /></a></div>\r\n" + 
    	 		"             <div class=\"TOP_anniu\" style=\"width:80px\"></div>\r\n" + 
    	 		"             <div class=\"TOP_sc\"><a href=\"index.htm\" style=\"margin:0 0 0 0;\">返回首页</a>&nbsp;|&nbsp;<a href=\"readhistory.htm\" style=\"margin:0 0 0 0\">阅读历史</a>&nbsp;|&nbsp;<a href=\"scorehistory.htm\">学分银行</a></div>\r\n" + 
    	 		"          </div>   \r\n" + 
    	 		"     </div>\r\n" + 
    	 		"     <div class=\"Main\">\r\n" + 
    	 		"        <div class=\"weizhi\" style=\"float:left;\">您的位置： <a href=\"index.htm\">首页</a> >> <a href=\"list.htm?id=&page=1\"></a> >> <span  id=\"a_title\">【教育资讯】全省教育脱贫攻坚现场调度会召开</span> </div>\r\n" + 
    	 		"        <div class=\"TOP_anniu\" style=\"float:right; margin:20px 0 0 0\"><!--a href=\"\">已读</a--></div> \r\n" + 
    	 		"        <div style=\"clear:both\"></div>\r\n" + 
    	 		"            <iframe scrolling=\"no\" id=\"main\" style=\"height:50px;visibility:inherit; width:100%;z-index:1; margin:20px 0 0 0\" id=\"iframe\" overflow:hidden; frameborder=\"0\" src=\"http://rwsy.gsres.cn:81/html/2019/07/190705223846679141133//content.html?rn=1521417495\"></iframe>\r\n" + 
    	 		"		<input type=\"text\" style=\"width:100px; height:32px; float:left; margin:20px 0 30px 10px\" id=\"courseNum1\" name=\"courseNum1\"><div class=\"TOP_anniu\" style=\"margin:20px 0 30px 0; float:left \"><a href=\"#*\" onclick=\"read();\">回复</a><a href=\"#*\" onclick=\"history.back();\">返回</a></div>\r\n" + 
    	 		"        </div>\r\n" + 
    	 		"     </div>  \r\n" + 
    	 		" </center> \r\n" + 
    	 		"\r\n" + 
    	 		"<script>window._bd_share_config={\"common\":{\"bdSnsKey\":{},\"bdText\":\"\",\"bdMini\":\"2\",\"bdMiniList\":false,\"bdPic\":\"\",\"bdStyle\":\"0\",\"bdSize\":\"16\"},\"slide\":{\"type\":\"slide\",\"bdImg\":\"2\",\"bdPos\":\"right\",\"bdTop\":\"147.5\"}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>   \r\n" + 
    	 		"</body>\r\n" + 
    	 		"</html>\r\n" + 
    	 		"\r\n" + 
    	 		"";
    	 Document doc = Jsoup.parse(content);
     	Elements liArray = doc.select("#myForm").select("input");
     	for(int i=0;i<liArray.size();i++){
     		if ("id".equals(liArray.get(i).attr("name")) ){
     			System.out.println(liArray.get(i).attr("value"));
     			break;
     		};
     	}
 		
     	
     }
     
}

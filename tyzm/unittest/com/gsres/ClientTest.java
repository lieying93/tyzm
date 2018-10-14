package com.gsres;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import net.sf.json.JSONObject;

public class ClientTest {
	
    @Test
    public void parseGetLoginName(){
    	Client client = new Client();
    	String result = "{\"status\":200,\"data\":{\"inputType\":\"id_card_no\",\"oldLoginName\":\"\\u738b\\u6dd1\\u7434\",\"loginName\":\"gsres_iflytek_d9dd7d07a7ca9c9a5c7c33f510afddc8\",\"isOldUser\":1,\"reviewStatus\":\"110002\",\"userType\":\"002\",\"userId\":\"3162000039000195022\"}}";
    	JSONObject userInfo = client.parseGetLoginName(result);
    	System.out.println("userInfo.isOldUser="+userInfo.getString("isOldUser"));
    	System.out.println("userInfo.loginName="+userInfo.getString("loginName"));
    	System.out.println("userInfo.oldLoginName="+userInfo.getString("oldLoginName"));
    	System.out.println("userInfo.reviewStatus ="+userInfo.getString("reviewStatus"));
    }
    @Test
    public void parseGetLoginTicket(){
    	
    	String result = "('{ \\\r\n" + 
    			"    \"result\": \"success\", \\\r\n" + 
    			"    \"code\": 1000, \\\r\n" + 
    			"    \"message\": \"login required\", \\\r\n" + 
    			"    \"data\": {\"lt\" : \"LT-3007213-ObhDEJKRHIzpJlDqnys2dovZAEmhCL\", \"execution\" : \"e1s1\"} \\\r\n" + 
    			"  }')";
    	 result = result.replace("/\t/g", "");
    	 result = result.replace("\\", "");
    	 result = result.replace("(", "");
    	 result = result.replace(")", "");
    	 result = result.replace("'", "");
    	 System.out.println(result);
    	JSONObject jsonObj = JSONObject.fromObject(result);
    	String status = jsonObj.getString("result");
    	String code = jsonObj.getString("code");
    	String message = jsonObj.getString("message");
    	System.out.println("status="+status);
    	System.out.println("code="+code);
    	System.out.println("message="+message);
    	JSONObject jsonData = jsonObj.getJSONObject("data");
    	System.out.println("jsonData-lt="+jsonData.getString("lt"));
    	System.out.println("jsonData-execution="+jsonData.getString("execution"));
    }
    @Test
    public void unRead(){
    	String content ="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n" + 
    			"<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n" + 
    			"<head>\r\n" + 
    			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />\r\n" + 
    			"<title>甘肃教师学苑</title>\r\n" + 
    			"<link href=\"http://rwsy.gsres.cn:81/front/style/style.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n" + 
    			"<script type=\"text/javascript\" src=\"http://rwsy.gsres.cn:81/skins/metal_blue/js/jquery-1.7.1.min.js\"></script>\r\n" + 
    			"<script>\r\n" + 
    			"var total = 1;\r\n" + 
    			"try{total = parseInt('1');}catch (e) {}\r\n" + 
    			"var __page = 1;\r\n" + 
    			"function goPage(page){\r\n" + 
    			"	var curPage;\r\n" + 
    			"	if(page<1){\r\n" + 
    			"		curPage = 1;\r\n" + 
    			"	}else if(page >= total){\r\n" + 
    			"		curPage = total;\r\n" + 
    			"	}else{\r\n" + 
    			"		curPage = page;\r\n" + 
    			"	}\r\n" + 
    			"	location.href = \"unreadhistory.htm?id=&page=\"+curPage;\r\n" + 
    			"}\r\n" + 
    			"</script>\r\n" + 
    			"<style>\r\n" + 
    			".yd{ background:#fff; padding:2px 6px; color:#666}\r\n" + 
    			".wd{ background:#fff; padding:2px 6px; color:#000; }\r\n" + 
    			".ydt{font-size:14px; font-weight:bold; color:#FF6600; float:right}\r\n" + 
    			".wdt{font-size:14px; font-weight:bold; color:#436c0e; float:right}\r\n" + 
    			"</style>\r\n" + 
    			"</head>\r\n" + 
    			"\r\n" + 
    			"<body>\r\n" + 
    			"   <div class=\"TOP\">\r\n" + 
    			"          <div class=\"TOP_mian\">\r\n" + 
    			"             <div class=\"TOP_title\">甘肃教师学苑</div>\r\n" + 
    			"             <div class=\"TOP_anniu\" style=\"width:200px\"></div>\r\n" + 
    			"             <div class=\"TOP_sc\"><a href=\"index.htm\" style=\"margin:0 0 0 90px\">返回首页</a></div>\r\n" + 
    			"          </div>   \r\n" + 
    			"     </div>\r\n" + 
    			"     <div class=\"Main\">\r\n" + 
    			"         <div class=\"weizhi\">您的位置： <a href=\"index.htm\">首页</a> >>2017-2018年未阅读记录 [共208条，未读6条]</div>\r\n" + 
    			"         <ul>\r\n" + 
    			"         \r\n" + 
    			"            <li><a href=\"content.htm?id=180413183214593240201&sid=&page=1\">\r\n" + 
    			"                 <!-- div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/html/2018/04/180413193947964240220//zoom18041319394909240228.jpg\"  onerror=\"this.src='http://rwsy.gsres.cn:81/front/images/default.jpg'\"/></div-->\r\n" + 
    			"                 <div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/front/images/default.jpg\"/></div>\r\n" + 
    			"                 <div class=\"text_qishu\">【教学风采】大师的教育初心<div class=\"wdt\">（未读）</div></div>\r\n" + 
    			"                 <div class=\"datetime\">2018-04-13</div></a>\r\n" + 
    			"            </li>\r\n" + 
    			"            <div class=\"DL_line\" style=\"clear:both\"></div>\r\n" + 
    			"          \r\n" + 
    			"            <li><a href=\"content.htm?id=180413183111288240200&sid=&page=1\">\r\n" + 
    			"                 <!-- div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/html/2018/04/180413193930663240217//zoom180413193930726240219.jpg\"  onerror=\"this.src='http://rwsy.gsres.cn:81/front/images/default.jpg'\"/></div-->\r\n" + 
    			"                 <div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/front/images/default.jpg\"/></div>\r\n" + 
    			"                 <div class=\"text_qishu\">【教育资讯】李克强：全面加强乡村小规模学校和乡镇寄宿制学校建设<div class=\"wdt\">（未读）</div></div>\r\n" + 
    			"                 <div class=\"datetime\">2018-04-13</div></a>\r\n" + 
    			"            </li>\r\n" + 
    			"            <div class=\"DL_line\" style=\"clear:both\"></div>\r\n" + 
    			"          \r\n" + 
    			"            <li><a href=\"content.htm?id=180413183042834240199&sid=&page=1\">\r\n" + 
    			"                 <!-- div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/html/2018/04/180413193923550240208//zoom180413193924751240216.jpg\"  onerror=\"this.src='http://rwsy.gsres.cn:81/front/images/default.jpg'\"/></div-->\r\n" + 
    			"                 <div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/front/images/default.jpg\"/></div>\r\n" + 
    			"                 <div class=\"text_qishu\">【人文素养】生命的欢愉和对生活的热爱——汪曾祺小说赏析<div class=\"wdt\">（未读）</div></div>\r\n" + 
    			"                 <div class=\"datetime\">2018-04-13</div></a>\r\n" + 
    			"            </li>\r\n" + 
    			"            <div class=\"DL_line\" style=\"clear:both\"></div>\r\n" + 
    			"          \r\n" + 
    			"            <li><a href=\"content.htm?id=180413183029152240198&sid=&page=1\">\r\n" + 
    			"                 <!-- div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/html/2018/04/180413193911850240204//zoom180413193912177240207.jpg\"  onerror=\"this.src='http://rwsy.gsres.cn:81/front/images/default.jpg'\"/></div-->\r\n" + 
    			"                 <div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/front/images/default.jpg\"/></div>\r\n" + 
    			"                 <div class=\"text_qishu\">最美教师—环县木钵镇杨新庄小学教师王宝花（总第313期—2018.4.13）<div class=\"wdt\">（未读）</div></div>\r\n" + 
    			"                 <div class=\"datetime\">2018-04-13</div></a>\r\n" + 
    			"            </li>\r\n" + 
    			"            <div class=\"DL_line\" style=\"clear:both\"></div>\r\n" + 
    			"          \r\n" + 
    			"            <li><a href=\"content.htm?id=180411204821763240929&sid=&page=1\">\r\n" + 
    			"                 <!-- div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/html/2018/04/180411211413856240948//zoom180411211414356240952.jpg\"  onerror=\"this.src='http://rwsy.gsres.cn:81/front/images/default.jpg'\"/></div-->\r\n" + 
    			"                 <div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/front/images/default.jpg\"/></div>\r\n" + 
    			"                 <div class=\"text_qishu\">【教学风采】批评教育的艺术（二）<div class=\"wdt\">（未读）</div></div>\r\n" + 
    			"                 <div class=\"datetime\">2018-04-11</div></a>\r\n" + 
    			"            </li>\r\n" + 
    			"            <div class=\"DL_line\" style=\"clear:both\"></div>\r\n" + 
    			"          \r\n" + 
    			"            <li><a href=\"content.htm?id=180411204747989240928&sid=&page=1\">\r\n" + 
    			"                 <!-- div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/html/2018/04/180411211343156240930//zoom180411211343234240933.jpg\"  onerror=\"this.src='http://rwsy.gsres.cn:81/front/images/default.jpg'\"/></div-->\r\n" + 
    			"                 <div class=\"left_img\"><img src=\"http://rwsy.gsres.cn:81/front/images/default.jpg\"/></div>\r\n" + 
    			"                 <div class=\"text_qishu\">定西90后女老师患癌离世 “最后一课”令人动容（总第312期—2018.4.11）<div class=\"wdt\">（未读）</div></div>\r\n" + 
    			"                 <div class=\"datetime\">2018-04-11</div></a>\r\n" + 
    			"            </li>\r\n" + 
    			"            <div class=\"DL_line\" style=\"clear:both\"></div>\r\n" + 
    			"            \r\n" + 
    			"         </ul>\r\n" + 
    			"         <div class=\"yema\">\r\n" + 
    			"		每页20条记录，当前第1/1页&nbsp;&nbsp;&nbsp;\r\n" + 
    			"           <a href=\"#*\" onclick=\"goPage(__page - 1 );\">上一页</a><a href=\"#*\" onclick=\"goPage(__page + 1);\">下一页</a>\r\n" + 
    			"           &nbsp;&nbsp;&nbsp;&nbsp;转到&nbsp;\r\n" + 
    			"           <input type=\"text\" name=\"number\" style=\"width:40px; height:24px\" onblur=\"goPage($(this).val());\"/>\r\n" + 
    			"           &nbsp;页</span> \r\n" + 
    			"         </div>\r\n" + 
    			"     </div>  \r\n" + 
    			"       \r\n" + 
    			"</body>\r\n" + 
    			"</html>";
    	Document doc = Jsoup.parse(content);
    	Elements liArray = doc.select("div.Main").select("ul").select("li");
    	
    	for(int i =0; i< liArray.size();i++){
    		System.out.println("第"+(i+1)+"条未读记录:");
    		System.out.println("标题:"+liArray.get(i).select("div.text_qishu").html());
    		System.out.println("链接:"+liArray.get(i).select("a").attr("href"));
    	}
    }
}

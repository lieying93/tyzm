package com.gsres.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gsres.entity.Teacher;

public class DomTeach {
	public static List<Teacher>  getTeachers(){
		List<Teacher> Teachers = new ArrayList<Teacher>();
		 //创建一个DocumentBuilderFactory的对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        //创建一个DocumentBuilder的对象
	        try {
	            //创建DocumentBuilder对象
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            //通过DocumentBuilder对象的parser方法加载用户s.xml文件到当前项目下
	            Document document = db.parse("./config/properties.xml");
	            //获取所有用户节点的集合
	            NodeList teacherList = document.getElementsByTagName("teacher");
	            //通过nodelist的getLength()方法可以获取用户List的长度
	            System.out.println("一共有" + teacherList.getLength() + "用户");
	            //遍历每一个用户节点
	            for (int i = 0; i < teacherList.getLength(); i++) {
	            	Teacher teacher = new Teacher();
	                System.out.println("=================下面开始遍历第" + (i + 1) + "用户的内容=================");
	                //通过 item(i)方法 获取一个teacher节点，nodelist的索引值从0开始
	                Node teacherNode = teacherList.item(i);
	                //获取用户节点的所有属性集合
	                NamedNodeMap attrs = teacherNode.getAttributes();
	                System.out.println("第 " + (i + 1) + "用户共有" + attrs.getLength() + "个属性");
	                //遍历用户的属性
	                for (int j = 0; j < attrs.getLength(); j++) {
	                    //通过item(index)方法获取用户节点的某一个属性
	                    Node attr = attrs.item(j);
	                    //获取属性名
	                    System.out.print("属性名：" + attr.getNodeName());
	                    //获取属性值
	                    System.out.println("--属性值" + attr.getNodeValue());
	                }
	                //解析用户节点的子节点
	                NodeList childNodes = teacherNode.getChildNodes();
	                for (int k = 0; k < childNodes.getLength(); k++) {
	                    //区分出text类型的node以及element类型的node
	                    if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
	                        //获取了element类型节点的节点名
	                        String nodeName = childNodes.item(k).getNodeName();
	                        //获取了element类型节点的节点值
	                        String nodeValue = childNodes.item(k).getTextContent();
	                        switch (nodeName){ 
	                            case "name":
	                            	teacher.setName(nodeValue);
	                               break;
	                            case "inputName":
	                            	teacher.setInputName(nodeValue);
	                            	break;
	                            case "passwd":
	                            	teacher.setPasswd(nodeValue);
	                            	break;
	                            case "loginName":
	                            	teacher.setLoginName(nodeValue);
	                            	break;
	                            case "password":
	                            	teacher.setPassword(nodeValue);
	                            	break;
	                        }
	                        
	                    }
	                    
	                }
	                System.out.println(teacher.toString());
	                Teachers.add(teacher);
	                System.out.println("======================结束遍历第" + (i + 1) + "用户的内容=================");
	            }
	        }  catch (Exception e) {
	            e.printStackTrace();
	        }        
        return Teachers;
	}
	public static void main(String[] args) {
		//定义要解析的用户名
		List<Teacher> teachers = DomTeach.getTeachers();
		for (Teacher teacher : teachers) {
			if("622822196703044525".equals(teacher.getInputName())){
				System.out.println(teacher.toString());
			}
		}
	}

}

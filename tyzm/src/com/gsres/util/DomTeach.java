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
		 //����һ��DocumentBuilderFactory�Ķ���
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        //����һ��DocumentBuilder�Ķ���
	        try {
	            //����DocumentBuilder����
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            //ͨ��DocumentBuilder�����parser���������û�s.xml�ļ�����ǰ��Ŀ��
	            Document document = db.parse("./config/properties.xml");
	            //��ȡ�����û��ڵ�ļ���
	            NodeList teacherList = document.getElementsByTagName("teacher");
	            //ͨ��nodelist��getLength()�������Ի�ȡ�û�List�ĳ���
	            System.out.println("һ����" + teacherList.getLength() + "�û�");
	            //����ÿһ���û��ڵ�
	            for (int i = 0; i < teacherList.getLength(); i++) {
	            	Teacher teacher = new Teacher();
	                System.out.println("=================���濪ʼ������" + (i + 1) + "�û�������=================");
	                //ͨ�� item(i)���� ��ȡһ��teacher�ڵ㣬nodelist������ֵ��0��ʼ
	                Node teacherNode = teacherList.item(i);
	                //��ȡ�û��ڵ���������Լ���
	                NamedNodeMap attrs = teacherNode.getAttributes();
	                System.out.println("�� " + (i + 1) + "�û�����" + attrs.getLength() + "������");
	                //�����û�������
	                for (int j = 0; j < attrs.getLength(); j++) {
	                    //ͨ��item(index)������ȡ�û��ڵ��ĳһ������
	                    Node attr = attrs.item(j);
	                    //��ȡ������
	                    System.out.print("��������" + attr.getNodeName());
	                    //��ȡ����ֵ
	                    System.out.println("--����ֵ" + attr.getNodeValue());
	                }
	                //�����û��ڵ���ӽڵ�
	                NodeList childNodes = teacherNode.getChildNodes();
	                for (int k = 0; k < childNodes.getLength(); k++) {
	                    //���ֳ�text���͵�node�Լ�element���͵�node
	                    if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
	                        //��ȡ��element���ͽڵ�Ľڵ���
	                        String nodeName = childNodes.item(k).getNodeName();
	                        //��ȡ��element���ͽڵ�Ľڵ�ֵ
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
	                System.out.println("======================����������" + (i + 1) + "�û�������=================");
	            }
	        }  catch (Exception e) {
	            e.printStackTrace();
	        }        
        return Teachers;
	}
	public static void main(String[] args) {
		//����Ҫ�������û���
		List<Teacher> teachers = DomTeach.getTeachers();
		for (Teacher teacher : teachers) {
			if("622822196703044525".equals(teacher.getInputName())){
				System.out.println(teacher.toString());
			}
		}
	}

}

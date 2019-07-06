package com.gsres.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

	/**
	 * 读取文件信息
	 * @param sFileName
	 * @return
	 */
	public static String readFile(String sFileName) {
		StringBuffer tmp=new StringBuffer();
		BufferedReader br = null;
		try{
			br=new BufferedReader(new FileReader(sFileName));
			char[] buf = new char[1024];
			int len;
			while((len = br.read(buf)) > 0) {   
				tmp.append(buf,0,len);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(br!=null)br.close();
			}catch(Exception e){};
		}
		return tmp.toString();
	}
	public static String readFileLine(String sFileName) {

		BufferedReader br = null;
		String content="";
		try{
			br=new BufferedReader(new FileReader(sFileName));
			
			content = br.readLine();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(br!=null)br.close();
			}catch(Exception e){};
		}
		return content.trim();
	}
	public static boolean writeFile(String sFileName,byte[] bContent) {
		BufferedOutputStream bos=null;
		try{
			bos = new BufferedOutputStream(new FileOutputStream(sFileName));
			bos.write(bContent);
			bos.flush();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try{
				if(bos!=null)bos.close();
			}catch(Exception e){};
		}
	}
	
	/**
	 * 截取文件的名称
	 * @param 完整文件名
	 * @return 文件名称
	 */
	public static String getFileName(String f) {   
        String fname = "";   
        int i = f.lastIndexOf('.');   
  
        if (i > 0 &&  i < f.length() - 1) {   
            fname = f.substring(0,i);   
        }        
        return fname;   
    }
	
	/**
	 * 截取文件的后缀
	 * @param 完整文件名
	 * @return 文件后缀
	 */
	public static String getExtension(String f) {   
        String ext = "";
        int i = f.lastIndexOf('.');   
  
        if (i > 0 &&  i < f.length() - 1) {   
            ext = f.substring(i+1);   
        }        
        return ext;   
    }
	
	/**
	 * 创建目录
	 * @param fileName
	 * @return
	 */
	public static File mkFolder(String fileName) {
       File f = new File(fileName);
       if (!f.exists()) {
           f.mkdir();
       }
       return f;
    }
	
	/**
	 * 创建文件
	 * @param fileName
	 * @return
	 */
	public static File mkFile(String fileName) {
       File f = new File(fileName);
       try {
           f.createNewFile();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return f;
    }
}

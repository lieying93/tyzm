package com.gsres.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ImagePreProcess {
	  
      public static String  getCodeName(String reply) throws InterruptedException{
    	  try {
    		//  sb.append(" H:\\nircmd.exe elevate cmd /c  " );
    		  Process  process = Runtime.getRuntime().exec(" cmd /c H:\\Tesseract-OCR\\tesseract.exe  " +reply + " C:\\result");
    		 //Process  process = Runtime.getRuntime().exec(" cmd /c  copy  H:\\code.jpg H:\\result.jpg");
    		 process.waitFor();
//             InputStream in = process.getInputStream();
//             InputStreamReader reader = new InputStreamReader(in);
//             BufferedReader br = new BufferedReader(reader);
//             StringBuffer sb = new StringBuffer();
//             String message;
//             while((message = br.readLine()) != null) {
//                 sb.append(message);
//             }
//             System.out.println(sb);


          } catch (IOException e) {
              e.printStackTrace();
          }
    	  String codeNum=  FileUtil.readFile("C:\\result.txt");
    	  return codeNum;
         }
      public static void main(String[] args) throws InterruptedException {
    	  ImagePreProcess imp = new ImagePreProcess();
    	  imp.getCodeName("K:\\git_hub\\tyzm\\tyzm\\img\\code.jpg");
    	  String codeNum=  FileUtil.readFile("C:\\result.txt");
    	  System.out.println(codeNum.trim());
	}
}


package com.gsres;


import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
 
public class JScriptEngine {
	public static void main(String[] args) throws Exception {   
		
		 //����һ���ű����������
        ScriptEngineManager manager = new ScriptEngineManager();
        //��ȡһ��ָ�������ƵĽű�����
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            
            // FileReader�Ĳ���Ϊ��Ҫִ�е�js�ļ���·��
            engine.eval(new FileReader("K:\\git_hub\\myutils\\tyzm\\js\\RSAUtil.js"));
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine;
                //�ӽű������з���һ�������ӿڵ�ʵ��
                Methods executeMethod = invocable.getInterface(Methods.class);
                //ִ��ָ����js����
                System.out.println(executeMethod.getPassword("wsq0304"));//
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


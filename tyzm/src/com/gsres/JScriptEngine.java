package com.gsres;


import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
 
public class JScriptEngine {
	public static void main(String[] args) throws Exception {   
		
		 //创建一个脚本引擎管理器
        ScriptEngineManager manager = new ScriptEngineManager();
        //获取一个指定的名称的脚本引擎
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            
            // FileReader的参数为所要执行的js文件的路径
            engine.eval(new FileReader("K:\\git_hub\\myutils\\tyzm\\js\\RSAUtil.js"));
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine;
                //从脚本引擎中返回一个给定接口的实现
                Methods executeMethod = invocable.getInterface(Methods.class);
                //执行指定的js方法
                System.out.println(executeMethod.getPassword("wsq0304"));//
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


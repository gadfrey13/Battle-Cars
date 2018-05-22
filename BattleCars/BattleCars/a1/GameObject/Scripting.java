package a1.GameObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import myGameEngine.NPC.NPCcontroller;

import java.io.*;
import java.util.*;

public class Scripting {
	private String scriptFileName;
	private ScriptEngine jsEngine;
	private Invocable invocableEngine;
	private Object ret;
	public Scripting(String script) {
		ScriptEngineManager factory = new ScriptEngineManager();
		scriptFileName = script;
		jsEngine = factory.getEngineByName("js");
		invocableEngine = (Invocable) jsEngine;
	}

	public void apply(String name, NPCcontroller object) {
		this.executeScript(jsEngine, scriptFileName);
		try {
			ret = invocableEngine.invokeFunction(name, object);//name is the name of the function										     //object is the parameter
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void applyText(String name, String object) {
		this.executeScript(jsEngine, scriptFileName);
		try {
			ret = invocableEngine.invokeFunction(name, object);//name is the name of the function										     //object is the parameter
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void applyMat(String name, String object) {
		this.executeScript(jsEngine, scriptFileName);
		try {
			ret = invocableEngine.invokeFunction(name, object);//name is the name of the function										     //object is the parameter
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Object scriptValue(){
		return ret;
	}

	private void executeScript(ScriptEngine engine, String scriptFileName) {
		try {
			FileReader fileReader = new FileReader(scriptFileName);
			engine.eval(fileReader); // execute the script statements in the
										// file
			fileReader.close();
		} catch (FileNotFoundException e1) {
			System.out.println(scriptFileName + " not found " + e1);
		} catch (IOException e2) {
			System.out.println("IO problem with " + scriptFileName + e2);
		} catch (ScriptException e3) {
			System.out.println("ScriptException in " + scriptFileName + e3);
		} catch (NullPointerException e4) {
			System.out.println("Null ptr exception in " + scriptFileName + e4);
		}
	}
}

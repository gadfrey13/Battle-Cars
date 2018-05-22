package myGameEngine;

import a1.GameObject.GameObject;
import ray.rage.Engine;
import ray.rage.rendersystem.gl4.GL4RenderSystem;

public class GameHub {
	private GL4RenderSystem rs,rs2;
	private float elapsTime = 0.0f;
	private String elapsTimeStr, counterStr, dispStr;
	private int elapsTimeSec, counter = 0;
	private Engine engine;
	private int size = 0;
	private int score = 0;
	private int actualScore = 0;
	private int showScore = 0;
	private int realScore = 0;
	private int finalScore = 0;
	private GameObject object;
	public GameHub(Engine engine, GameObject object){
		this.engine = engine;
		this.object = object;
		
	}
	/**
	 * Game hub. x and y starts at 0,0 bottom left corner
	 * @param str
	 * @param x
	 * @param y
	 */
	public void setHub(int x, int y){
		
		rs = (GL4RenderSystem) engine.getRenderSystem();
		elapsTime += engine.getElapsedTimeMillis();
		elapsTimeSec = Math.round(elapsTime/1000.0f);
		elapsTimeStr = Integer.toString(elapsTimeSec);
		dispStr = "Lives: " + object.getLives() + " Missiles: " +  object.getNumberOfMissile();
		rs.setHUD(dispStr, x, y);
		//this.shownScore();
	}
	
	/**
	 * Game hub. x and y starts at 0,0 bottom left corner
	 * @param str
	 * @param x
	 * @param y
	 */
	public void setHub2(int x, int y){
		rs = (GL4RenderSystem) engine.getRenderSystem();
		elapsTime += engine.getElapsedTimeMillis();
		elapsTimeSec = Math.round(elapsTime/1000.0f);
		elapsTimeStr = Integer.toString(elapsTimeSec);
		dispStr = "Earth Time = " + elapsTimeStr + " Score: " + realScore;
		rs.setHUD2(dispStr, x, y);
		this.shownScore();
	}
	
	public void shownScore(){
		realScore = (int) Math.rint(score/13);
		//int value = Math.abs(this.size-40);
		//realScore = value;
	}
	
	public void add(){
		score = score +  1;
	}
	
	public void sendSize(int size){
		this.size = size;
	}
	
	public int getScore(){
		return realScore;
	}
}

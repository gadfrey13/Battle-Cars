package a1.GameObject;

import java.io.IOException;

import myGameEngine.MyPhysicsEngine;
import myGameEngine.MyPhysicsObject;
import ray.rage.Engine;
import ray.rage.scene.SceneManager;

public class Barrier {
	private SceneManager sm;
	private Engine eng;
	private MyPhysicsEngine physicsEngine;
	public Barrier(SceneManager sm, Engine eng,MyPhysicsEngine physicsEngine){
		this.sm = sm;
		this.eng = eng;
		this.physicsEngine = physicsEngine;
	}
	
	
	public void init() throws IOException{
		eastWestWall();
		northSouthWall();
		//plateu();
	}
	
	public void eastWestWall() throws IOException {
		// LeftBarrier
		GameObject leftWall = new GameObject(sm, "eastWall", "cube.obj", "wall");
		//leftWall.createWall();
		leftWall.create();
		leftWall.addTexture("blue.jpeg");
		leftWall.getNode().setLocalPosition(48.0f, 2.0f, 0.0f);
		leftWall.getNode().scale(1.0f, 1.0f, 100.0f);
		MyPhysicsObject leftObject = new MyPhysicsObject(leftWall.getNode(), "wallEast", this.physicsEngine);
		leftObject.create();
		// RightBarrier
		GameObject rightWall = new GameObject(sm, "westWall", "cube.obj", "wall");
		//rightWall.createWall();
		rightWall.create();
		rightWall.addTexture("blue.jpeg");
		rightWall.getNode().setLocalPosition(-48.0f, 2.0f, 0.0f);
		rightWall.getNode().scale(1.0f, 1.0f, 100.0f);
		MyPhysicsObject rightObject = new MyPhysicsObject(rightWall.getNode(), "wallWest", this.physicsEngine);
		rightObject.create();
	}
	
	public void northSouthWall() throws IOException {
		// LeftBarrier
		GameObject leftWall = new GameObject(sm, "southWall", "cube.obj", "wall");
		//leftWall.createWall();
		leftWall.create();
		leftWall.addTexture("blue.jpeg");
		leftWall.getNode().setLocalPosition(1.0f, 2.0f, 48.0f);
		leftWall.getNode().scale(100.0f, 1.0f, 1.0f);
		MyPhysicsObject leftObject = new MyPhysicsObject(leftWall.getNode(), "wallNorth", this.physicsEngine);
		leftObject.create();
		// RightBarrier
		GameObject rightWall = new GameObject(sm, "northWall", "cube.obj", "wall");
		//rightWall.createWall();
		rightWall.create();
		rightWall.addTexture("blue.jpeg");
		rightWall.getNode().setLocalPosition(1.0f, 2.0f, -48.0f);
		rightWall.getNode().scale(100.0f, 1.0f, 1.0f);
		MyPhysicsObject rightObject = new MyPhysicsObject(rightWall.getNode(), "wallSouth", this.physicsEngine);
		rightObject.create();
		
		
	}
	
	public void plateu() throws IOException{
		GameObject plateuone = new GameObject(sm,"plateuone","cube.obj","plateu");
		plateuone.create();
		plateuone.addTexture("X_Axis.png");
		plateuone.getNode().setLocalPosition(-20.0f,0.0f,30.0f);
		plateuone.getNode().scale(1.0f,4.0f,17.0f);
		MyPhysicsObject oneObject = new MyPhysicsObject(plateuone.getNode(), "plateuone", this.physicsEngine);
		oneObject.create();
	}
	
	
}

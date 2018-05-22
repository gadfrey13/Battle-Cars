package myGameEngine;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import ray.physics.PhysicsEngine;
import ray.physics.PhysicsObject;
import ray.rage.Engine;
import ray.rage.asset.texture.Texture;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.physics.PhysicsEngineFactory;

public class MyPhysicsEngine {
	private SceneManager sm;
	private Stack<SceneNode> sceneobject = new Stack<SceneNode>();
	private Stack<MyPhysicsObject> physicsobject = new Stack<MyPhysicsObject>();
	private PhysicsEngine physicsEng;
	private Engine eng;
	private PhysicsObject myphysicsobject;
	public MyPhysicsEngine(SceneManager sm, Engine eng){
		this.sm = sm;
		this.eng = eng;
	}
	
	public void initPhysicsSystem(){
		String engine = "ray.physics.JBullet.JBulletPhysicsEngine";
		float[] gravity = {0,-9f,0};
		physicsEng = PhysicsEngineFactory.createPhysicsEngine(engine);
		physicsEng.initSystem();
		physicsEng.setGravity(gravity);
	}
	
	/*
	public void createRagePhysicsWorld() {
		float mass = 1.0f;
		float up[] = { 0,  1, 0 };
		double[] temptf;
		if (this.sceneobject != null) {
			Stack<MyPhysicsObject> temp = this.physicsobject;
			System.out.println(temp.size());
			for (int i = 0; i < temp.size(); i++) {
				MyPhysicsObject obj = temp.pop();
				System.out.println(obj.getNode().getName());
				SceneNode t = obj.getNode();
				String type = obj.getPhysicsType();
				System.out.println(type);
				temptf = toDoubleArray(t.getLocalTransform().toFloatArray());
				if (t.getPhysicsObject() == null) {//True if scenenode does not have a physics object attach
					System.out.println("Inside the NUll");
					if (type.equals("car")) {
						PhysicsObject phyobject = physicsEng.addSphereObject(physicsEng.nextUID(), mass, temptf, 2.0f);
						phyobject.setBounciness(1.0f);
						phyobject.setFriction(100.0f);
						t.setPhysicsObject(phyobject);
						System.out.println(t.getPhysicsObject());
					} else if (type == "ground") {
						temptf = toDoubleArray(t.getLocalTransform().toFloatArray());
						PhysicsObject gndPlaneP = physicsEng.addStaticPlaneObject(physicsEng.nextUID(), temptf, up,
								0.0f);
						gndPlaneP.setBounciness(1.0f);
						gndPlaneP.setFriction(10.0f);
						t.setPhysicsObject(gndPlaneP);
						//t.setLocalPosition(0, 10.8f, -2);
						//t.scale(100f, .05f, 100f)
					}
				}
			}
		}
	}
	*/
	
	
	public PhysicsObject getMyPhysicsObject(){
		return this.myphysicsobject;
	}
	
	public float[] toFloatArray(double[] arr) {
		if (arr == null)
			return null;
		int n = arr.length;
		float[] ret = new float[n];
		for (int i = 0; i < n; i++) {
			ret[i] = (float) arr[i];
		}
		return ret;
	}

	public double[] toDoubleArray(float[] arr) {
		if (arr == null)
			return null;
		int n = arr.length;
		double[] ret = new double[n];
		for (int i = 0; i < n; i++) {
			ret[i] = (double) arr[i];
		}
		return ret;
	}
	
	public PhysicsEngine getPhysicsEngine(){
		return this.physicsEng;
	}

	
}

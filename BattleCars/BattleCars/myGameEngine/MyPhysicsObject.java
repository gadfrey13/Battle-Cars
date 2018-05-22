package myGameEngine;

import ray.physics.PhysicsEngine;
import ray.physics.PhysicsObject;
import ray.rage.scene.SceneNode;

public class MyPhysicsObject {
	private SceneNode node;
	private String type;
	private MyPhysicsEngine physicsEng;
	private int id;
	private PhysicsObject phyobject;
	public MyPhysicsObject(SceneNode node, String type,MyPhysicsEngine physicsEng){
		this.node = node;
		this.type = type;
		this.physicsEng = physicsEng;
		id = physicsEng.getPhysicsEngine().nextUID();
	}
	
	public SceneNode getNode(){
		return this.node;
	}
	
	public String getPhysicsType(){
		return this.type;
	}
	
	public void create() {
		float mass = 1.0f;
		float up[] = { 0, 1, 0 };
		double[] temptf = physicsEng.toDoubleArray(node.getLocalTransform().toFloatArray());
		if (type.equals("car")) {
			float dimension[] = {0.0f,1.0f,1.0f};
			phyobject = physicsEng.getPhysicsEngine()
					.addSphereObject(id, mass, temptf, 0.3f);
			phyobject.setBounciness(0.1f);
			phyobject.setDamping(0.3f, 0.7f);
			node.setPhysicsObject(phyobject);
			System.out.println(node.getPhysicsObject());
		} else if (type == "ground") {
			phyobject = physicsEng.getPhysicsEngine()
					.addStaticPlaneObject(id, temptf, up, 0.0f);
			phyobject.setBounciness(0.0f);
			//phyobject.setFriction(0.5f);
			node.setPhysicsObject(phyobject);
		}else if (type == "missile"){
			//phyobject = physicsEng.getPhysicsEngine().addCapsuleObject(id,5.0f,temptf, 1.0f, 1.0f);
			phyobject = physicsEng.getPhysicsEngine()
					.addSphereObject(id, mass, temptf, 0.1f);
			phyobject.setBounciness(1.0f);
			node.setPhysicsObject(phyobject);	
		}else if (type.contains("wall")){
			barrier(type,temptf);
		}else if(type.contains("plateu")){
			plateu(type,temptf);
		}
	}
	
	public void barrier(String wall,double[] temptf){
		if(wall == "wallEast"){
			float size[] = {1.0f,1.0f,100.0f};
			phyobject = physicsEng.getPhysicsEngine().addBoxObject(id, 1000.0f, temptf,size);
			phyobject.setBounciness(0.2f);
			node.setPhysicsObject(phyobject);
		}else if(wall == "wallWest"){
			float size[] = {1.0f,1.0f,100.0f};
			phyobject = physicsEng.getPhysicsEngine().addBoxObject(id, 1000.0f, temptf,size);
			phyobject.setBounciness(0.2f);
			node.setPhysicsObject(phyobject);
		}else if(wall == "wallNorth"){
			float size[] = {100.0f,1.0f,1.0f};
			phyobject = physicsEng.getPhysicsEngine().addBoxObject(id, 1000.0f, temptf,size);
			phyobject.setBounciness(0.2f);
			node.setPhysicsObject(phyobject);
		}else if(wall == "wallSouth"){
			float size[] = {100.0f,1.0f,1.0f};
			phyobject = physicsEng.getPhysicsEngine().addBoxObject(id, 1000.0f, temptf,size);
			phyobject.setBounciness(0.2f);
			node.setPhysicsObject(phyobject);
		}
	}
	
	public void plateu(String plateu,double[] temptf){
		if(plateu == "plateuone"){
			float size[] = {17.0f,0.0f,30.0f};
			phyobject = physicsEng.getPhysicsEngine().addBoxObject(id, 1000.0f, temptf,size);
			phyobject.setBounciness(0.5f);
			node.setPhysicsObject(phyobject);
		}
	}
	
	public PhysicsObject getPhysicsObject(){
		return this.phyobject;
	}
}

package a1.GameObject;

import java.util.UUID;

import ray.rage.scene.Entity;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3;

public class GhostNPC {
	private int id;
	private SceneNode node;
	private Entity entity;
	private Vector3 position;
	
	public GhostNPC(int id, Vector3 postion){
		this.id = id;
		this.position = position;
	}
	
	public void setPosition(Vector3 position){
		node.setLocalPosition(position);
	}
	
	public void getPosition(){
		node.getLocalPosition();
	}
	
	public int getID(){
		return id;
	}
	
	public void setNode(SceneNode node){
		this.node = node;
	}
	
	public void setEntity(Entity entity){
		this.entity = entity;
	}
	
	public SceneNode getNode(){
		return this.node;
	}
	
	public Vector3 originalPosition(){
		return position;
	}
	
}

package myGameEngine.CameraControl.ObjectControl;

import a1.GameObject.GameObject;
import myGameEngine.MyPhysicsObject;
import myGameEngine.ProtocolClient;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class MoveForwardNodeAction extends AbstractInputAction {
	
	private SceneNode node,cameraNode;
	private Camera camera;
	private Vector3f camObjectDistance;
	private ProtocolClient protclient;
	private MyPhysicsObject physicsObject;
	private GameObject object;
	private float ac = 5;
	private float[] preVelocity;
	public MoveForwardNodeAction(Camera c,SceneNode n,Vector3f camObjectDistance){
		node = n;
		camera = c;
		this.camObjectDistance = camObjectDistance;
	}
	
	public MoveForwardNodeAction(Camera c, SceneNode n){
		this.node = n;
		this.camera = c;
	}
	
	public MoveForwardNodeAction(Camera mycam, SceneNode n, ProtocolClient protclient){
		camera = mycam;
		node = n;
		this.protclient = protclient;
	}
	
	public MoveForwardNodeAction(Camera mycam, GameObject object, ProtocolClient protclient,MyPhysicsObject physicsObject){
		camera = mycam;
		node = object.getNode();
		this.protclient = protclient;
		this.physicsObject = physicsObject;
		this.object = object;
	}

	@Override
	public void performAction(float time, Event ev) {
		
		System.out.println("Move the " + ev.getComponent());
		//node.moveForward(0.1f);
		if(ac < 10){
			ac = ac + 0.2f;
		}else{
			ac = 5;
		}
		Vector3 up = object.updateVerticalPosition();
		System.out.println("Previous " + up);
		up = up.add(Vector3f.createFrom(2.0f, 0.0f, 2.0f));
		this.physicsObject.getPhysicsObject().applyForce(node.getLocalForwardAxis().x()*ac,0.0f, node.getLocalForwardAxis().z()*ac,this.node.getLocalPosition().x(),0.0f, this.node.getLocalPosition().z());
		this.preVelocity = this.physicsObject.getPhysicsObject().getLinearVelocity();
		float[] velocity = this.physicsObject.getPhysicsObject().getLinearVelocity();
		
		System.out.println("Velocity " + velocity[0] + " " + velocity[1] + " " + velocity[2]);
		if(!object.movingSound().getIsPlaying()){
			object.movingSound().play();
			this.protclient.sendMoveMessage("sound", 0.0f);
		}
		
		try{
		//System.out.println(this.protclient);
		System.out.println(ev);
		this.protclient.sendMoveMessage("forward",ac);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

}

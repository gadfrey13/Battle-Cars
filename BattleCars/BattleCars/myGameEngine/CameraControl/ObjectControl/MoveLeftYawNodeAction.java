package myGameEngine.CameraControl.ObjectControl;

import myGameEngine.MyCamera;
import myGameEngine.MyPhysicsObject;
import myGameEngine.ProtocolClient;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.Engine;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rage.scene.SkeletalEntity;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;
import static ray.rage.scene.SkeletalEntity.EndType.*;
public class MoveLeftYawNodeAction extends AbstractInputAction {
	private Camera camera;
	private SceneNode node;
	private Vector3f camObjectDistance;
	private MyCamera mycam;
	private SceneNode cameraNode;
	private Engine eng;
	private ProtocolClient protclient;
	private MyPhysicsObject physicsObject;
	public MoveLeftYawNodeAction(Camera c, SceneNode n,Vector3f camObjectDistance){
		camera = c;
		node = n;
		this.camObjectDistance = camObjectDistance;
	}
	
	public MoveLeftYawNodeAction(Camera c, SceneNode n,Vector3f camObjectDistance,Engine eng){
		camera = c;
		node = n;
		this.camObjectDistance = camObjectDistance;
		this.eng = eng;
	}
	
	public MoveLeftYawNodeAction(MyCamera mycam, SceneNode n,Vector3f camObjectDistance){
		camera = mycam.getCamera();
		node = n;
		this.camObjectDistance = camObjectDistance;
		this.mycam = mycam;
		this.cameraNode = mycam.getCameraNode();
	}
	
	public MoveLeftYawNodeAction(MyCamera mycam, SceneNode n,Vector3f camObjectDistance,Engine eng){
		camera = mycam.getCamera();
		node = n;
		this.camObjectDistance = camObjectDistance;
		this.mycam = mycam;
		this.cameraNode = mycam.getCameraNode();
		this.eng = eng;
	}
	
	public MoveLeftYawNodeAction(Camera mycam, SceneNode n, ProtocolClient protclient){
		camera = mycam;
		node = n;
		this.protclient = protclient;
	}
	
	public MoveLeftYawNodeAction(MyCamera mycam, SceneNode n, ProtocolClient protclient){
		this.mycam = mycam;
		node = n;
		this.protclient = protclient;
	}
	
	public MoveLeftYawNodeAction(MyCamera mycam, SceneNode n, ProtocolClient protclient,Engine eng){
		this.mycam = mycam;
		node = n;
		this.protclient = protclient;
		this.eng = eng;
	}
	
	public MoveLeftYawNodeAction(Camera mycam, SceneNode n, ProtocolClient protclient,MyPhysicsObject physicsObject){
		camera = mycam;
		node = n;
		this.protclient = protclient;
		this.physicsObject = physicsObject;
	}
	
	@Override
	public void performAction(float arg0, Event arg1) {

		System.out.println("Move Yaw Left");
		//System.out.println("CameraNode Location" + cameraNode.getLocalPosition());
		Angle angle = Degreef.createFrom(1.0f);
		node.yaw(angle);
		//this.physicsObject.getPhysicsObject().applyForce(node.getLocalForwardAxis().x(),0.0f, node.getLocalForwardAxis().z(),this.node.getLocalPosition().x(),0.0f, this.node.getLocalPosition().z());
		try{
		//SkeletalEntity manSE = (SkeletalEntity) eng.getSceneManager().getEntity("player");
		//manSE.playAnimation("turnleft",0.5f, LOOP, 0);
		this.protclient.sendMoveMessage("yawleft",1.0f);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	
	}
}

package myGameEngine.CameraControl.ObjectControl;

import java.io.IOException;

import a1.GameObject.GameObject;
import myGameEngine.MyPhysicsObject;
import myGameEngine.ProtocolClient;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3f;

public class Fire extends AbstractInputAction {

	private SceneNode node,cameraNode;
	private Camera camera;
	private Vector3f camObjectDistance;
	private ProtocolClient protclient;
	private MyPhysicsObject physicsObject;
	private GameObject object;
	
	public Fire(Camera mycam, GameObject object, ProtocolClient protclient,MyPhysicsObject physicsObject){
		camera = mycam;
		node = object.getNode();
		this.protclient = protclient;
		this.physicsObject = physicsObject;
		this.object = object;
	}
	
	@Override
	public void performAction(float arg0, Event arg1) {
		System.out.println("Fired Missile Input");

			try {
				object.fireMissile(object);
				this.protclient.sendMoveMessage("fire", 0.0f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
	

}

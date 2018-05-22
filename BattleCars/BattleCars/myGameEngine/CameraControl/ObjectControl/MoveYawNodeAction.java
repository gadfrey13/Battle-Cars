package myGameEngine.CameraControl.ObjectControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3f;

public class MoveYawNodeAction extends AbstractInputAction  {
	private Camera camera;
	private SceneNode node;
	private Vector3f camObjectDistance;
	public MoveYawNodeAction(Camera c, SceneNode n,Vector3f camObjectDistance){
		camera = c;
		node = n;
		this.camObjectDistance = camObjectDistance;
	}
	
	public MoveYawNodeAction(Camera c, SceneNode n){
		this.camera = c;
		this.node = n;
	}
	@Override
	public void performAction(float time, Event e) {
		System.out.println("Move Yaw");
		System.out.println("Yaw Event " + e.getValue());
		float a = 0.0f;
		if(e.getValue() > 0.0f){
			a = -0.7f;
		}else if(e.getValue() < 0.0f){
			a = 0.7f;
		}
		node.yaw(Degreef.createFrom(a));
	}
}

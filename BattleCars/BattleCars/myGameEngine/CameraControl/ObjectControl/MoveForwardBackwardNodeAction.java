package myGameEngine.CameraControl.ObjectControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3f;

public class MoveForwardBackwardNodeAction extends AbstractInputAction {
	private Camera camera;
	private SceneNode node;
	private Vector3f camObjectDistance;
	public MoveForwardBackwardNodeAction(Camera c, SceneNode n,Vector3f camObjectDistance){
		camera = c;
		node = n;
		this.camObjectDistance = camObjectDistance;
	}
	
	public MoveForwardBackwardNodeAction(Camera c, SceneNode n){
		this.camera = c;
		this.node = n;
	}
	@Override
	public void performAction(float time, Event e) {
		if(e.getValue() > 0){
			System.out.println("Move Node Backward");
			node.moveBackward(0.05f);

		}else if(e.getValue() < 0){
			System.out.println("Move Node Forward");
			node.moveForward(0.05f);
	
		}
	}

}

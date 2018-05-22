package myGameEngine.CameraControl.ObjectControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3f;

public class MovePitchNodeAction extends AbstractInputAction {
	private Camera camera;
	private SceneNode node;
	private Vector3f camObjectDistance;
	public MovePitchNodeAction(Camera c, SceneNode n,Vector3f camObjectDistance){
		camera = c;
		node = n;
		this.camObjectDistance = camObjectDistance;
	}
	
	public MovePitchNodeAction(Camera c, SceneNode n){
		this.node = n;
		this.camera = c;
	}
	@Override
	public void performAction(float time, Event e) {
		System.out.println("Move Pitch");
		float a = 0.0f;
		if(e.getValue() > 0.0f){
			a = 0.3f;
		}else if(e.getValue() < 0.0f){
			a = -0.3f;
		}

	}
}

package myGameEngine.CameraControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class MoveRightLeftAction extends AbstractInputAction {
	private Camera camera;
	
	public MoveRightLeftAction(Camera c){
		camera = c;
	}

	@Override
	public void performAction(float time, Event e) {
		if(e.getValue() > 0){
			System.out.println("MoveRight Action Iniated");
			Vector3f v = camera.getRt();
			Vector3f p = camera.getPo();
			Vector3f p1 =
			 (Vector3f) Vector3f.createFrom(0.05f*v.x(), 0.05f*v.y(), 0.05f*v.z());
			Vector3f p2 = (Vector3f) p.add((Vector3)p1);
			camera.setPo((Vector3f)Vector3f.createFrom(p2.x(),p2.y(),p2.z()));
		}else if(e.getValue() < 0){
			
			System.out.println("MoveLeft Action Iniated");
			Vector3f v = camera.getRt();
			Vector3f p = camera.getPo();
			Vector3f p1 =
			 (Vector3f) Vector3f.createFrom(0.05f*v.x(), 0.05f*v.y(), 0.05f*v.z());
			Vector3f p2 = (Vector3f) p.sub((Vector3)p1);
			camera.setPo((Vector3f)Vector3f.createFrom(p2.x(),p2.y(),p2.z()));
		}
	}
	

}

package myGameEngine.CameraControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class PitchUpAction extends AbstractInputAction {
	private Camera camera;
	
	public PitchUpAction(Camera c){
		camera = c;
	}

	@Override
	public void performAction(float arg0, Event arg1) {
		System.out.println("PitchUp Action Iniated");
		float tilt = 1.0f;
		Vector3 f = camera.getFd();
		Vector3 r = camera.getRt();
		Vector3 u = camera.getUp();
		Vector3 fn = (f.rotate(Degreef.createFrom(0.2f * tilt), r)).normalize();
		Vector3 un = (u.rotate(Degreef.createFrom(0.2f * tilt), r)).normalize();
		camera.setFd((Vector3f)Vector3f.createFrom(fn.x(),fn.y(),fn.z()));
		camera.setUp((Vector3f)Vector3f.createFrom(un.x(),un.y(),un.z()));
		
	}
}

package myGameEngine.CameraControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class PitchAction extends AbstractInputAction {
	private Camera camera;
	public PitchAction(Camera c){
		camera = c;
	}
	@Override
	public void performAction(float time, Event e) {
		System.out.println("Pitch Action");
		float tilt;
		Vector3 f = camera.getFd();
		Vector3 r = camera.getRt();
		Vector3 u = camera.getUp();
		if (e.getValue() < 0.0) tilt = -1.0f;
		 else if (e.getValue() > 0.0) tilt = 1.0f;
		 else tilt = 0.0f;
		Vector3 fn = (f.rotate(Degreef.createFrom(0.1f * tilt), r)).normalize();
		Vector3 un = (u.rotate(Degreef.createFrom(0.1f * tilt), r)).normalize();
		camera.setFd((Vector3f)Vector3f.createFrom(fn.x(),fn.y(),fn.z()));
		camera.setUp((Vector3f)Vector3f.createFrom(un.x(),un.y(),un.z()));
	}

}

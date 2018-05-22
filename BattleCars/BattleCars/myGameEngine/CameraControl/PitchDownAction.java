package myGameEngine.CameraControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.rendersystem.Viewport;
import ray.rage.scene.Camera;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Matrix3f;
import ray.rml.Vector3;
import ray.rml.Vector3f;


public class PitchDownAction extends AbstractInputAction {
	private Camera camera;
	
	public PitchDownAction(Camera c){
		camera = c;
	}
	@Override
	public void performAction(float time, Event e) {
		System.out.println("PitchDown Action Iniated");
		float tilt = -1.0f;
		Vector3 f = camera.getFd();
		Vector3 r = camera.getRt();
		Vector3 u = camera.getUp();
		Vector3 fn = (f.rotate(Degreef.createFrom(0.2f * tilt), r)).normalize();
		Vector3 un = (u.rotate(Degreef.createFrom(0.2f * tilt), r)).normalize();
		camera.setFd((Vector3f)Vector3f.createFrom(fn.x(),fn.y(),fn.z()));
		camera.setUp((Vector3f)Vector3f.createFrom(un.x(),un.y(),un.z()));
		/*
		Angle angle = Degreef.createFrom(0.5f);;
	    Vector3f right = camera.getRt();
		Vector3f up = camera.getUp();
		Matrix3f matrixRotate = null;
		Vector3f vectorRotate = null;
		Vector3f foward = camera.getFd();
		matrixRotate.rotate(angle,right);
		vectorRotate.rotate(angle, right);
		up.mult(vectorRotate);
		foward.mult(vectorRotate);
		*/
	}
	

}

package myGameEngine.CameraControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class YawRightAction extends AbstractInputAction {

	private Camera camera;
	
	public YawRightAction(Camera c){
		camera = c;
	}
	
	@Override
	public void performAction(float time, Event e) {
		
		System.out.println("YawRight Action Iniated");
		float tilt = -1.0f;
		Vector3 f = camera.getFd();
		Vector3 r = camera.getRt();
		Vector3 u = camera.getUp();
		Vector3 rn = (r.rotate(Degreef.createFrom(0.2f * tilt), u)).normalize();
		Vector3 fn = (f.rotate(Degreef.createFrom(0.2f * tilt), u)).normalize();
		camera.setFd((Vector3f)Vector3f.createFrom(fn.x(),fn.y(),fn.z()));
		camera.setRt((Vector3f)Vector3f.createFrom(rn.x(),rn.y(),rn.z()));
		/*
		Angle angle = Degreef.createFrom(time*0.05f);
		Vector3f position = camera.getPo();
		Vector3f V = camera.getUp();
		position = (Vector3f) position.rotate(angle, V);
		position = (Vector3f) position.normalize();
		position = (Vector3f) position.div(4);
		System.out.println(position);
		camera.setPo(position);
		*/
		/*
		Vector3f rotation = (Vector3f) Vector3f.createFrom(1.0f, 1.0f, 1.0f);
		System.out.println("Current Posistion " + camera.getPo());
		
		Vector3f N = camera.getFd();
		Vector3f V = camera.getUp();
		Vector3f U = camera.getRt();
		
		float dotValue = U.dot(N);
		float uMagnitude = U.length();
		float nMagnitude = N.length();
		float newAngle = (float) Math.acos(dotValue/(uMagnitude*nMagnitude));
		
		
		Angle myAngle = Degreef.createFrom((time*0.05f)/2);
		//Angle myAngle = Degreef.createFrom(newAngle);
		
		System.out.println("This the time value " + time);
		System.out.println(myAngle);
		
		rotation = (Vector3f) rotation.rotate(myAngle, V);
		
		System.out.println(rotation);
		
		N = (Vector3f) N.mult(rotation);
		U = (Vector3f) U.mult(rotation);
		
		System.out.println("U " + U);
		System.out.println("N " + N);
		
		N = (Vector3f) N.normalize();
		U = (Vector3f) U.normalize();
		
		N = (Vector3f) N.div(4);
		U = (Vector3f) U.div(4);
		
		System.out.println("U after Normalize " + U);
		System.out.println("N after Normalize " + N);
		//camera.setRt((Vector3f) U.normalize());
		//camera.setFd((Vector3f) N.normalize());
		
		Vector3f newCamLoc = (Vector3f) camera.getPo().add(N).add(U);
		
		System.out.println("New Cam Location" + newCamLoc);
		
		camera.setPo((Vector3f) Vector3f.createFrom(newCamLoc.x(),newCamLoc.y(), newCamLoc.z()));
		*/
	}

}

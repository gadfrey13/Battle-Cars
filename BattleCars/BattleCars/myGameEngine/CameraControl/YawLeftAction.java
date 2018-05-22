package myGameEngine.CameraControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Matrix3f;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class YawLeftAction extends AbstractInputAction {

	private Camera camera;
	public YawLeftAction(Camera c){
		camera = c;
	}
	@Override
	public void performAction(float time, Event e) {
		// TODO Auto-generated method stub
		System.out.println("YawLeft Action Iniated");
		float tilt = 1.0f;
		Vector3 f = camera.getFd();
		Vector3 r = camera.getRt();
		Vector3 u = camera.getUp();
		Vector3 rn = (r.rotate(Degreef.createFrom(0.2f * tilt), u)).normalize();
		Vector3 fn = (f.rotate(Degreef.createFrom(0.2f * tilt), u)).normalize();
		camera.setFd((Vector3f)Vector3f.createFrom(fn.x(),fn.y(),fn.z()));
		camera.setRt((Vector3f)Vector3f.createFrom(rn.x(),rn.y(),rn.z()));
		
		/*
		Vector3f rotation = (Vector3f) Vector3f.createFrom(1.0f, 1.0f, 1.0f);
		//Vector3f rotation = camera.getPo();
		System.out.println("Current Posistion" + camera.getPo());
		
		Vector3f N = camera.getFd();
		Vector3f V = camera.getUp();
		Vector3f U = camera.getRt();
		
		float dotValue = U.dot(N);
		float uMagnitude = U.length();
		float nMagnitude = N.length();
		float newAngle = (float) Math.acos(dotValue/(uMagnitude*nMagnitude));
		
		
		Angle myAngle = Degreef.createFrom((time*-0.05f)/2);
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
		//System.out.println(U.normalize());
		*/
		/*
		Vector3f currentPosition = (Vector3f) camera.getPo(); //So this the current position: Starting: 0 0 0
		Vector3f V = camera.getUp(); //So you rotate from the V axis
		System.out.println("Original Location" + currentPosition);
		
		Angle myAngle = Degreef.createFrom(5.0f);
		
		Vector3f U = camera.getRt();
		Vector3f N = camera.getFd();
		System.out.println("Original U" + U);
		System.out.println("Original V" + V);
		
		Vector3f newU = (Vector3f) U.rotate(myAngle, V).div(100);
		Vector3f newN = (Vector3f) N.rotate(myAngle, V).div(100);
		System.out.println("New U" + newU);
		System.out.println("New N" + newN);
		
		float dotValue = newU.dot(newN);
		float uMagnitude = newU.length();
		float nMagnitude = newN.length();
		
		float newAngle = (float) Math.acos(dotValue/(uMagnitude*nMagnitude));
		
		Angle angle = Degreef.createFrom(newAngle);
		
		System.out.println(angle);
		
		System.out.println(camera.getFrustum().getFieldOfViewY());
		
	
		
		System.out.println(camera.getFrustum().getFieldOfViewY());
		
		//Vector3f newPosition = (Vector3f) currentPosition.add(newU.x(),0.0f,newN.z());
		//System.out.println("New Position" + newPosition);
		
		camera.getFrustum().setFieldOfViewY(camera.getFrustum().getFieldOfViewY().add(angle));
		
		//camera.setPo((Vector3f) Vector3f.createFrom(newPosition.x(), newPosition.y(), newPosition.z()));
		
		
		
		/*
		 * So I want to rotate around the V axis. To get the new N and U values. To be able to change the position
		 * of the camera. Example So I
		 * 'm position 0 0 0 then I click the yaw left key the value should change in 
		 * the U and N position while V stays the same. 
		 */

		
		/*
		
		Vector3f U = camera.getRt();//X Axis
		Vector3f V = camera.getUp();//Y Axis
		Vector3f N = camera.getFd();//Z Axis
		
		System.out.println(currentPosition);
		System.out.println(currentPosition.length());
		
		System.out.println(U);
		System.out.println(V);
		System.out.println(N);
		
		Angle angleU = Degreef.createFrom(currentPosition.x());
		Angle angleN = Degreef.createFrom(currentPosition.z());
		
		
		System.out.println(angleU.valueRadians());
		System.out.println(angleN.valueRadians());
		
		Vector3f newU = (Vector3f) currentPosition.rotate(angleU, V);
		Vector3f newN = (Vector3f) currentPosition.rotate(angleN, V);
		
		System.out.println(newU);
		System.out.println(newN);
		
		Vector3f changePosition = (Vector3f) currentPosition.add(newU).add(newN);
		
		System.out.println(changePosition);
		
		camera.setPo((Vector3f) Vector3f.createFrom(changePosition.x(), changePosition.y(), changePosition.z()));
		System.out.println(camera.getPo());
		
		*/
	}

}
